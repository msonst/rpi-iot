package de.sonsts.rpi.iot;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author sonst00m
 *
 */
public class AppMainMQTT
{
    public static void main(String[] args) throws MqttException, InterruptedException
    {

        System.out.println("== START PUBLISHER ==");
        MqttClient client = new MqttClient("tcp://192.168.0.8:1883", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();

        int counter = 0;

        while (true)
        {
            message.setPayload(Integer.toString(counter % 11).getBytes());
            client.publish("mqtt/int", message);

            message.setPayload(("Message " + Integer.toString(counter % 11)).getBytes());
            client.publish("mqtt/str", message);

            System.out.println(message.toString());
            counter++;

            Thread.sleep(2000);
        }
        // client.disconnect();
        // System.out.println("== END PUBLISHER ==");
    }

}
