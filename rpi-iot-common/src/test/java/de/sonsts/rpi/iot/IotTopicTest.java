package de.sonsts.rpi.iot;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.sonsts.rpi.iot.communication.common.messaage.cannel.IotTopic;

public class IotTopicTest
{

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void test()
    {
        IotTopic reply = IotTopic.REPLY;
        IotTopic combined = reply.combine("test");
        
        System.out.println(reply);
        System.out.println(combined);
    }
}
