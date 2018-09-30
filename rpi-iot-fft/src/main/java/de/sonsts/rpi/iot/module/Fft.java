package de.sonsts.rpi.iot.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.Quality;
import de.sonsts.rpi.iot.communication.common.SpectrumValue;
import de.sonsts.rpi.iot.communication.common.messaage.AbstractMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessageFactory;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.producer.MessageProducer;
import de.sonsts.rpi.iot.communication.producer.SendCallback;

//@formatter:off
/******************************************************************************
 * Compilation: javac InplaceFFT.java Execution: java InplaceFFT n Dependencies: Complex.java
 *
 * Compute the FFT of a length n complex sequence in-place. Uses a non-recursive version of the Cooley-Tukey FFT. Runs in O(n log n) time.
 *
 * Reference: Algorithm 1.6.1 in Computational Frameworks for the Fast Fourier Transform by Charles Van Loan.
 *
 *
 * Limitations ----------- - assumes n is a power of 2
 *
 * 
 ******************************************************************************/
// @formatter:on
public class Fft
{
    private static final long NANOSECONDS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);

    private MessageProducer<DocumentMessage<SampleValuePayload<SpectrumValue>>> mProducer;
    private HashMap<Integer, String> mMapping;

    public Fft(MessageProducer<DocumentMessage<SampleValuePayload<SpectrumValue>>> producer, HashMap<Integer, String> mapping)
    {
        mProducer = producer;
        mMapping = mapping;
    }

    private int upper_power_of_two(int v)
    {
        int retVal = v;

        retVal--;
        retVal |= retVal >> 1;
        retVal |= retVal >> 2;
        retVal |= retVal >> 4;
        retVal |= retVal >> 8;
        retVal |= retVal >> 16;
        retVal++;

        return retVal;
    }

    public List<SpectrumValue> compute(List<DoubleSampleValue> values)
    {
        List<SpectrumValue> retVal = new ArrayList<SpectrumValue>();

        if (null != values)
        {
            DoubleSampleValue firstValue = values.get(0);
            DoubleSampleValue lastValue = values.get(values.size() - 1);

            double duration = lastValue.getTimeStamp().subtract(firstValue.getTimeStamp()).longNano() / (double) NANOSECONDS_PER_SECOND;
            int sampleCount = values.size();
            double samplingFrequency = (double) sampleCount / duration; // fs: samples per second
            int sa = upper_power_of_two(sampleCount); // TODO closest power of 2
            double stepValue = samplingFrequency / (double) sa;

            ComplexValue[] fftValues = new ComplexValue[sa];

            for (int i = 0; i < fftValues.length; i++)
            {
                DoubleSampleValue value = (i < sampleCount) ? values.get(i) : new DoubleSampleValue();

                fftValues[i] = new ComplexValue(value.getValue(), 0);
            }

            fft(fftValues);

            for (int i = 0; i < fftValues.length; i++)
            {
                retVal.add(new SpectrumValue(i, System.currentTimeMillis(), fftValues[i], i * stepValue, Quality.GOOD, sampleCount));
            }
        }

        return retVal;
    }

    // compute the FFT of x[], assuming its length is a power of 2
    public void fft(ComplexValue[] values)
    {
        // check that length is a power of 2
        int n = values.length;
        if (Integer.highestOneBit(n) != n)
        {
            throw new RuntimeException("n is not a power of 2");
        }

        // bit reversal permutation
        int shift = 1 + Integer.numberOfLeadingZeros(n);
        for (int k = 0; k < n; k++)
        {
            int j = Integer.reverse(k) >>> shift;
            if (j > k)
            {
                ComplexValue temp = values[j];
                values[j] = values[k];
                values[k] = temp;
            }
        }

        // butterfly updates
        for (int L = 2; L <= n; L = L + L)
        {
            for (int k = 0; k < L / 2; k++)
            {
                double kth = -2 * k * Math.PI / L;
                ComplexValue w = new ComplexValue(Math.cos(kth), Math.sin(kth));
                for (int j = 0; j < n / L; j++)
                {
                    ComplexValue tao = w.times(values[j * L + k + L / 2]);
                    values[j * L + k + L / 2] = values[j * L + k].minus(tao);
                    values[j * L + k] = values[j * L + k].plus(tao);
                }
            }
        }
    }

}
