package de.sonsts.rpi.iot;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author sonst00m
 *
 */
public class AppMainMQTTCoordinates
{
    public static String EMOP = "{ \"name\": \"Emergency Medical Operation\", \"lat\": 49.05, \"lon\": %d.35, \"SIDC\": \"ENOPA-------\", \"options\": { \"fillOpacity\":0.8 } }";
    public static String JOE = "{ \"name\":\"Joe\", \"lat\":50.05, \"lon\":%d.35, \"icon\":\"car\", \"iconColor\":\"darkred\" }";

    public static void main(String[] args) throws MqttException, InterruptedException
    {
        System.out.println("== START PUBLISHER ==");
        MqttClient client = new MqttClient("tcp://192.168.0.8:1883", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();

        int latoffset = 0;
        while (true)
        {
             message.setPayload(String.format(EMOP, 1 + (latoffset % 90)).getBytes());
             client.publish("mqtt/emop", message);

            message.setPayload(String.format(JOE, 50 + (latoffset % 90)).getBytes());
            client.publish("mqtt/joe", message);

            latoffset++;

            Thread.sleep(2000);
        }
    }
}