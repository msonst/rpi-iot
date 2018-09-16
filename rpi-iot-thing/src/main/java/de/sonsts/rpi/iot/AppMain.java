package de.sonsts.rpi.iot;

import java.io.IOException;
import java.util.Arrays;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import de.sonsts.rpi.i2c.sensor.ADXL345;
import de.sonsts.rpi.i2c.sensor.utils.SampleBean;

/**
 * @author sonst00m
 *
 */
public class AppMain
{
    public static void main(String[] args) throws Exception
    {
        ADXL345 adxl345 = null;
        I2CBus bus = null;
        I2CDevice dev = null;
        Thread adxl345Thread = null;

        ObjectMapper mapper =  JsonFactory.create();

        
        MqttClient client = new MqttClient("tcp://192.168.0.8:1883", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();

        bus = I2CFactory.getInstance(I2CBus.BUS_1);
        if (null == bus)
        {
            throw new Exception("Failed to get I2C");
        }

        try
        {
            dev = bus.getDevice(0x53);// ADXL345.ADXL345_DEVID);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null == dev)
        {
            throw new Exception("Failed to get device");
        }

        adxl345 = new ADXL345(dev, ADXL345.DataRate.BR3200, ADXL345.Range.R2G);

        if (null != adxl345)
        {
            adxl345.open();
            adxl345Thread = new Thread(adxl345);
            adxl345Thread.start();
        }

        while ((null != adxl345) && (null != adxl345Thread) && adxl345Thread.isAlive())
        {
            SampleBean[] values = adxl345.getSamples();

            if (0 != values.length)
            {
                String strMsg = mapper.toJson(values); 

                message.setPayload(strMsg.getBytes());
                client.publish("mqtt/gyro", message);

                System.out.println("Samples: " + values.length);
            }
            else
            {
                Thread.sleep(10);
            }
        }
    }
}
