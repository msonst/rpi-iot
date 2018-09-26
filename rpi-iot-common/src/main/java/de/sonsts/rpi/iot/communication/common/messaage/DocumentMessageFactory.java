package de.sonsts.rpi.iot.communication.common.messaage;

import java.util.List;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.SpectrumValue;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;

public class DocumentMessageFactory
{
    public static DocumentMessage<SampleValuePayload<DoubleSampleValue>> createDoubleSampleValueMessage(
            MappingPayloadDescriptor<Integer, String> descriptor, List<DoubleSampleValue> values)
    {
        DocumentMessage<SampleValuePayload<DoubleSampleValue>> retVal = new DocumentMessage<SampleValuePayload<DoubleSampleValue>>();

        retVal.setPayload(new SampleValuePayload<DoubleSampleValue>(descriptor, values));

        return retVal;
    }

    public static DocumentMessage<SampleValuePayload<DoubleSampleValue>> createDoubleSampleValueMessage(List<DoubleSampleValue>  values)
    {
        return createDoubleSampleValueMessage(null, values);
    }

    public static DocumentMessage<SampleValuePayload<ComplexValue>> createComplexValueMessage(
            MappingPayloadDescriptor<Integer, String> descriptor, List<ComplexValue> values)
    {
        DocumentMessage<SampleValuePayload<ComplexValue>> retVal = new DocumentMessage<SampleValuePayload<ComplexValue>>();

        retVal.setPayload(new SampleValuePayload<ComplexValue>(descriptor, values));

        return retVal;
    }

    public static DocumentMessage<SampleValuePayload<SpectrumValue>> createSpectrumValueMessage(
            MappingPayloadDescriptor<Integer, String> descriptor, List<SpectrumValue> values)
    {
        DocumentMessage<SampleValuePayload<SpectrumValue>> retVal = new DocumentMessage<SampleValuePayload<SpectrumValue>>();

        retVal.setPayload(new SampleValuePayload<SpectrumValue>(descriptor, values));

        return retVal;
    }
}
