package de.sonsts.rpi.iot.communication.common;

import java.util.Objects;

//@formatter:off
    /******************************************************************************
     *  Compilation:  javac Complex.java
     *  Execution:    java Complex
     *
     *  Data type for complex numbers.
     *
     *  The data type is "immutable" so once you create and initialize
     *  a Complex object, you cannot change it. The "final" keyword
     *  when declaring re and im enforces this rule, making it a
     *  compile-time error to change the .re or .im instance variables after
     *  they've been initialized.
     *
     *  % java Complex
     *  a            = 5.0 + 6.0i
     *  b            = -3.0 + 4.0i
     *  Re(a)        = 5.0
     *  Im(a)        = 6.0
     *  b + a        = 2.0 + 10.0i
     *  a - b        = 8.0 + 2.0i
     *  a * b        = -39.0 + 2.0i
     *  b * a        = -39.0 + 2.0i
     *  a / b        = 0.36 - 1.52i
     *  (a / b) * b  = 5.0 + 6.0i
     *  conj(a)      = 5.0 - 6.0i
     *  |a|          = 7.810249675906654
     *  tan(a)       = -6.685231390246571E-6 + 1.0000103108981198i
     *
     ******************************************************************************/
//@formatter:on
public class ComplexValue extends SampleValue
{
    private final double re; // the real part
    private final double im; // the imaginary part

    // create a new object with the given real and imaginary parts
    public ComplexValue(double real, double imag)
    {
        re = real;
        im = imag;
    }

    @Override
    public String toString()
    {
        return "ComplexValue [re=" + re + ", im=" + im + "]";
    }

    // return abs/modulus/magnitude
    public double abs()
    {
        return Math.hypot(re, im);
    }

    // return angle/phase/argument, normalized to be between -pi and pi
    public double phase()
    {
        return Math.atan2(im, re);
    }

    // return a new Complex object whose value is (this + b)
    public ComplexValue plus(ComplexValue b)
    {
        ComplexValue a = this; // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new ComplexValue(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public ComplexValue minus(ComplexValue b)
    {
        ComplexValue a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new ComplexValue(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public ComplexValue times(ComplexValue b)
    {
        ComplexValue a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new ComplexValue(real, imag);
    }

    // return a new object whose value is (this * alpha)
    public ComplexValue scale(double alpha)
    {
        return new ComplexValue(alpha * re, alpha * im);
    }

    // return a new Complex object whose value is the conjugate of this
    public ComplexValue conjugate()
    {
        return new ComplexValue(re, -im);
    }

    // return a new Complex object whose value is the reciprocal of this
    public ComplexValue reciprocal()
    {
        double scale = re * re + im * im;
        return new ComplexValue(re / scale, -im / scale);
    }

    // return the real or imaginary part
    public double re()
    {
        return re;
    }

    public double im()
    {
        return im;
    }

    // return a / b
    public ComplexValue divides(ComplexValue b)
    {
        ComplexValue a = this;
        return a.times(b.reciprocal());
    }

    // return a new Complex object whose value is the complex exponential of this
    public ComplexValue exp()
    {
        return new ComplexValue(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    public ComplexValue sin()
    {
        return new ComplexValue(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public ComplexValue cos()
    {
        return new ComplexValue(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public ComplexValue tan()
    {
        return sin().divides(cos());
    }

    // a static version of plus
    public static ComplexValue plus(ComplexValue a, ComplexValue b)
    {
        double real = a.re + b.re;
        double imag = a.im + b.im;
        ComplexValue sum = new ComplexValue(real, imag);
        return sum;
    }

    // See Section 3.3.
    public boolean equals(Object x)
    {
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        ComplexValue that = (ComplexValue) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    // See Section 3.3.
    public int hashCode()
    {
        return Objects.hash(re, im);
    }

}
