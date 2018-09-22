package de.sonsts.rpi.iot.utils;

import java.io.File;
import java.util.Arrays;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;

public class BrokerManager
{
    private static final String INITIAL_CONFIG_PATH = "../config/BrokerManager.config";
    private static final String PORT = "<your_port>";
    private final Broker broker = new Broker();

    public void startBroker() throws Exception
    {
        String cfgPath = "./src/test/java/"+BrokerManager.class.getPackage().getName().replace('.', '/') + "/config/BrokerManager.config";
        final BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", PORT);
        brokerOptions.setInitialConfigurationLocation(cfgPath);

        broker.startup(brokerOptions);
    }

    public void stopBroker()
    {
        broker.shutdown();
    }
}
