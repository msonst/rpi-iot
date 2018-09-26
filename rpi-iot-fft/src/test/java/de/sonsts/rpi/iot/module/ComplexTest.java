package de.sonsts.rpi.iot.module;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.sonsts.rpi.iot.communication.common.ComplexValue;

public class ComplexTest
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
    public void testComplex()
    {
        ComplexValue a = new ComplexValue(5.0, 6.0);
        ComplexValue b = new ComplexValue(-3.0, 4.0);

        System.out.println("a            = " + a);
        System.out.println("b            = " + b);
        System.out.println("Re(a)        = " + a.getRe());
        System.out.println("Im(a)        = " + a.getIm());
        System.out.println("b + a        = " + b.plus(a));
        System.out.println("a - b        = " + a.minus(b));
        System.out.println("a * b        = " + a.times(b));
        System.out.println("b * a        = " + b.times(a));
        System.out.println("a / b        = " + a.divides(b));
        System.out.println("(a / b) * b  = " + a.divides(b).times(b));
        System.out.println("conj(a)      = " + a.conjugate());
        System.out.println("|a|          = " + a.abs());
        System.out.println("tan(a)       = " + a.tan());
    }

}
