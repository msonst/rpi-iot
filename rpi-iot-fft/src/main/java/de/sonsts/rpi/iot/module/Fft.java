package de.sonsts.rpi.iot.module;

import java.util.Arrays;
import java.util.HashMap;

import de.sonsts.rpi.iot.communication.common.ComplexValue;
import de.sonsts.rpi.iot.communication.common.DoubleSampleValue;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessage;
import de.sonsts.rpi.iot.communication.common.messaage.DocumentMessageFactory;
import de.sonsts.rpi.iot.communication.common.messaage.payload.MappingPayloadDescriptor;
import de.sonsts.rpi.iot.communication.common.messaage.payload.SampleValuePayload;
import de.sonsts.rpi.iot.communication.producer.MessageProducer;

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
    private MessageProducer<DocumentMessage<SampleValuePayload<ComplexValue>>> mProducer;
    private HashMap<Integer, String> mMapping;

    public Fft(MessageProducer<DocumentMessage<SampleValuePayload<ComplexValue>>> producer, HashMap<Integer, String> mapping)
    {
        mProducer = producer;
        mMapping = mapping;
    }

    public void compute(DoubleSampleValue[] values)
    {
        if (null != values)
        {
            ComplexValue[] fftValuesX = new ComplexValue[values.length];
            ComplexValue[] fftValuesY = new ComplexValue[values.length];
            ComplexValue[] fftValuesZ = new ComplexValue[values.length];

            for (int i = 0; i < values.length; i++)
            {
                fftValuesX[i] = new ComplexValue(values[i].getX(), 0);
                fftValuesY[i] = new ComplexValue(values[i].getY(), 0);
                fftValuesZ[i] = new ComplexValue(values[i].getZ(), 0);
            }

            fft(fftValuesX);
            fft(fftValuesY);
            fft(fftValuesZ);

            if (null != mProducer)
            {
                DocumentMessage<SampleValuePayload<ComplexValue>> documentMessage = DocumentMessageFactory.createComplexValueMessage(
                        new MappingPayloadDescriptor<Integer, String>(mMapping), fftValuesX);
                mProducer.send(documentMessage);
                
                //TODO: payload descriptor new descriptor fft(signals, freq)
            }
        }
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
