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

        String messageString = "Testmessage";
        System.out.println("== START PUBLISHER ==");
        MqttClient client = new MqttClient("tcp://192.168.0.8:1883", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();
        // message.setQos(0);
        while (true)
        {
            messageString = System.currentTimeMillis() +  ": Testmessage";
            message.setPayload(messageString.getBytes());

            client.publish("mqtt/pub", message);
            System.out.println(messageString);
            Thread.sleep(1000);
        }
        // client.disconnect();
        // System.out.println("== END PUBLISHER ==");
    }

}
