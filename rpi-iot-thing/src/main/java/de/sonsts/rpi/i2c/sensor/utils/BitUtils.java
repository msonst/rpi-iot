package de.sonsts.rpi.i2c.sensor.utils;

public class BitUtils
{
    public static int setBits(int value, int mask)
    {
        return (value | mask);
    }

    public static int clearBits(int value, int mask)
    {
        return (value & (~mask));
    }

    public static int setValue(int valueToSet, int currentValue, int mask)
    {
        int currentValueCleared = clearBits(currentValue, mask);
        int i = 0;
        
        while (mask % 2 == 0 && mask != 0x00)
        {
            mask >>= 1;
            i++;
        }
        return setBits(valueToSet << i, currentValueCleared);
    }

    public static int twosComplement(int value)
    {
        if (value >= 0 && value <= 0x7f) return value;
        else return value - 0x100;
    }
}
