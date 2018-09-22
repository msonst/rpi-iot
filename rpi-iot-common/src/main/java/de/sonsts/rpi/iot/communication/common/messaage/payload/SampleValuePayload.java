package de.sonsts.rpi.iot.communication.common.messaage.payload;

import java.util.Arrays;

import de.sonsts.rpi.iot.communication.common.SampleValue;

public class SampleValuePayload<M extends SampleValue> implements MessagePayload
{
    private M[] values;
    
    private PayloadDescriptor payloadDescriptor;

    public SampleValuePayload()
    {
    }

    public SampleValuePayload(M... values)
    {
        this.values = values;
    }

    public SampleValuePayload(PayloadDescriptor payloadDescriptor, M... values)
    {
        this.values = values;
        this.payloadDescriptor =payloadDescriptor;
    }

    public M[] getValues()
    {
        return values;
    }

    public void setValues(M[] values)
    {
        this.values = values;
    }

    public PayloadDescriptor getPayloadDescriptor()
    {
        return payloadDescriptor;
    }

    public void setPayloadDescriptor(PayloadDescriptor payloadDescriptor)
    {
        this.payloadDescriptor = payloadDescriptor;
    }

    @Override
    public String toString()
    {
        return "SampleValuePayload [values=" + Arrays.toString(values) + ", payloadDescriptor=" + payloadDescriptor + "]";
    }
}
