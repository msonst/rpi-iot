package de.sonsts.rpi.iot;

import com.rabbitmq.client.*;

/**
 * @author sonst00m
 *
 */
public class AppMainAmgq
{
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.8");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        String message = getMessage(argv);
        while (true)
        {
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
            Thread.sleep(1);
        }
//        channel.close();
//        connection.close();
    }

    private static String getMessage(String[] strings)
    {
        if (strings.length < 1) return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter)
    {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++)
        {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
