package de.sonsts.rpi.iot;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import de.sonsts.rpi.i2c.sensor.ADXL345;

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

            while (true)
            {
                double[] values = adxl345.getCalValues();
                String strMsg = String.format("{\"ts\":%d, \"x\":%.4f, \"y\":%.4f, \"z\":%.4f}", System.currentTimeMillis(), values[0],
                        values[1], values[2]);
                
                System.out.println(strMsg);

                message.setPayload(strMsg.getBytes());
                client.publish("mqtt/gyro", message);

//                Thread.sleep(100);
            }
        }
    }
}
