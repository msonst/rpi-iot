package de.sonsts.rpi.iot.monitoring;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class SimpleAgent
{
    public SimpleAgent()
    {
    }

    public void register(Object monitoredObject) throws MalformedObjectNameException, InstanceAlreadyExistsException,
            MBeanRegistrationException, NotCompliantMBeanException
    {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        Class<? extends Object> moClass = monitoredObject.getClass();
        ObjectName moBeanName = null;
        moBeanName = new ObjectName(moClass.getSimpleName());
        server.registerMBean(monitoredObject, moBeanName);
    }
}
