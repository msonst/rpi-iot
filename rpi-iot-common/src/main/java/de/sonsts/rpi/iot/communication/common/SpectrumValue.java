package de.sonsts.rpi.iot.communication.common;

public class SpectrumValue extends SampleValue
{
    private double frequency;
    private ComplexValue complexValue;
    private int sampleCount;

    public SpectrumValue()
    {
    }

    public SpectrumValue(int signalId, long timeStamp, ComplexValue complexValue, double frequency, Quality quality, int sampleCount)
    {
        this.sampleCount = sampleCount;
        setComplexValue(complexValue);
        setFrequency(frequency);
        setQuality(quality);
    }

    public double getFrequency()
    {
        return frequency;
    }

    public void setFrequency(double frequency)
    {
        this.frequency = frequency;
    }

    public ComplexValue getComplexValue()
    {
        return complexValue;
    }

    public void setComplexValue(ComplexValue complexValue)
    {
        this.complexValue = complexValue;
    }

    @Override
    public String toString()
    {
        return "SpectrumValue [signalId=" + getSignalId() + ", timeStamp=" + getTimeStamp() + ", complexValue=" + getComplexValue()
                + ", frequency=" + getFrequency() + ", quality=" + getQuality() + "]";
    }

    public double getMag()
    {
        double abs = complexValue.abs();
        return (double) 2 / (double) sampleCount * abs;
    }
}
