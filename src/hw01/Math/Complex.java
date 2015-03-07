/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01.Math;

/**
 *
 * @author zf002
 */
public class Complex {

    private double real;
    private double imag;

    public Complex(double real, double imag) {
        this.imag = imag;
        this.real = real;

    }

    public double magnitude() {
        return Math.sqrt(real * real + imag * imag);
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imag;
    }

    public Complex add(Complex a) {
        double real = this.real + a.getReal();
        double imag = this.imag + a.getImaginary();
        return new Complex(real, imag);
    }

    public Complex sub(Complex a) {
        return this.add(a.negate());
    }

    public Complex negate() {
        return new Complex(-this.real, -this.imag);
    }

    public Complex mul(Complex a) {
        double real = this.real * a.getReal() - this.imag * a.getImaginary();
        double imag = this.real * a.getImaginary() + this.imag * a.getReal();
        return new Complex(real, imag);
    }

    public Complex mul(double a) {
        double real = this.real * a;
        double imag = this.imag * a;
        return new Complex(real, imag);
    }

    public Complex exp() {
        return new Complex(Math.exp(real) * Math.cos(this.imag), Math.exp(real) * Math.sin(this.imag));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Complex other = (Complex) obj;
        if (Double.doubleToLongBits(this.real) != Double.doubleToLongBits(other.real)) {
            return false;
        }
        if (Double.doubleToLongBits(this.imag) != Double.doubleToLongBits(other.imag)) {
            return false;
        }
        return true;
    }

}
