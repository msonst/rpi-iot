package de.sonsts.rpi.iot.communication.common.messaage;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.messaage.payload.CrudCommandPayload;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.common.messaage.payload.StatusEventPayload;

public class MessageTest
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
    public void testDocumentMessage() throws JsonGenerationException, JsonMappingException, IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        StringWriter jsonData = new StringWriter();

        List<DoubleSampleValue> values = new ArrayList<DoubleSampleValue>();
        List<ComplexValue> cValues = new ArrayList<ComplexValue>();

        HashMap<Integer, String> mapping = new HashMap<Integer, String>();
        for (int i = 0; i < 10000; i++)
        {
            mapping.put(i, "Signal" + i);
            values.add(new DoubleSampleValue(i, 45454, 4242, 4243, 4244));
            cValues.add(new ComplexValue(i, 0));
        }

        DocumentMessage<SampleValuePayload<DoubleSampleValue>> documentDSValueMessage = DocumentMessageFactory
                .createDoubleSampleValueMessage(new MappingPayloadDescriptor<Integer, String>(mapping),
                        values.toArray(new DoubleSampleValue[0]));
        objectMapper.writeValue(jsonData, documentDSValueMessage);
        documentDSValueMessage = objectMapper.readValue(jsonData.toString().getBytes(), DocumentMessage.class);

        DocumentMessage<SampleValuePayload<ComplexValue>> documentCMessage = DocumentMessageFactory.createComplexValueMessage(
                new MappingPayloadDescriptor<Integer, String>(mapping), cValues.toArray(new ComplexValue[0]));
        objectMapper.writeValue(jsonData, documentCMessage);
        documentCMessage = objectMapper.readValue(jsonData.toString().getBytes(), DocumentMessage.class);
    }

    @Test
    public void testCommandMessage() throws JsonGenerationException, JsonMappingException, IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        StringWriter jsonData = new StringWriter();

        jsonData = new StringWriter();

        CommandMessage<CrudCommandPayload> commandMessage = CommandMessageFactory.createCommandMessage(CrudCommandPayload.Operation.CREATE);

        objectMapper.writeValue(jsonData, commandMessage);
        // System.out.println(jsonData);

        commandMessage = objectMapper.readValue(jsonData.toString().getBytes(), CommandMessage.class);
        // System.out.println(documentMessage);
    }

    @Test
    public void testEventMessage() throws JsonGenerationException, JsonMappingException, IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        StringWriter jsonData = new StringWriter();

        jsonData = new StringWriter();

        EventMessage<StatusEventPayload> eventMessage = EventMessageFactory.createStatusEventMessage();

        objectMapper.writeValue(jsonData, eventMessage);
        System.out.println(jsonData);

        eventMessage = objectMapper.readValue(jsonData.toString().getBytes(), EventMessage.class);
        // System.out.println(documentMessage);
    }

}
