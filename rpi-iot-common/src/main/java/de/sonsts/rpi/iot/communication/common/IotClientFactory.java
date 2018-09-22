package de.sonsts.rpi.iot.communication.common;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class IotClientFactory
{
    public Connection getConnection() throws IOException, TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost("192.168.0.8");
        factory.setPort(5672);
        
        return factory.newConnection();
    }
}
