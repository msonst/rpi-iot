package de.sonsts.rpi.iot.communication.common.messaage.payload;

import java.util.Arrays;
import java.util.List;

import de.sonsts.rpi.iot.communication.common.SampleValue;

public class SampleValuePayload<M extends SampleValue> implements MessagePayload
{
    private List<M> values;
    
    private MappingPayloadDescriptor<Integer, String> payloadDescriptor;

    public SampleValuePayload()
    {
    }

    public SampleValuePayload(List<M> values)
    {
        this.values = values;
    }

    public SampleValuePayload(MappingPayloadDescriptor<Integer, String> payloadDescriptor, List<M> values)
    {
        this.values = values;
        this.payloadDescriptor =payloadDescriptor;
    }

    public List<M> getValues()
    {
        return values;
    }

    public void setValues(List<M> values)
    {
        this.values = values;
    }

    public MappingPayloadDescriptor<Integer, String> getPayloadDescriptor()
    {
        return payloadDescriptor;
    }

    public void setPayloadDescriptor(MappingPayloadDescriptor<Integer, String> payloadDescriptor)
    {
        this.payloadDescriptor = payloadDescriptor;
    }

    @Override
    public String toString()
    {
       return "SampleValuePayload [values=" +values + ", payloadDescriptor=" + payloadDescriptor + "]";
    }
}
