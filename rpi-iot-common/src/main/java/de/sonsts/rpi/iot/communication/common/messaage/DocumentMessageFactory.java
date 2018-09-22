package de.sonsts.rpi.iot.communication.common.messaage;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;

public class DocumentMessageFactory
{
    public static DocumentMessage<SampleValuePayload<DoubleSampleValue>> createDoubleSampleValueMessage(
            MappingPayloadDescriptor<Integer, String> descriptor, DoubleSampleValue[] values)
    {
        DocumentMessage<SampleValuePayload<DoubleSampleValue>> retVal = new DocumentMessage<SampleValuePayload<DoubleSampleValue>>();

        retVal.setPayload(new SampleValuePayload<DoubleSampleValue>(descriptor, values));

        return retVal;
    }

    public static DocumentMessage<SampleValuePayload<DoubleSampleValue>> createDoubleSampleValueMessage(DoubleSampleValue... values)
    {
        return createDoubleSampleValueMessage(null, values);
    }

    public static DocumentMessage<SampleValuePayload<ComplexValue>> createComplexValueMessage(
            MappingPayloadDescriptor<Integer, String> descriptor, ComplexValue[] values)
    {
        DocumentMessage<SampleValuePayload<ComplexValue>> retVal = new DocumentMessage<SampleValuePayload<ComplexValue>>();

        retVal.setPayload(new SampleValuePayload<ComplexValue>(descriptor, values));

        return retVal;
    }
}
