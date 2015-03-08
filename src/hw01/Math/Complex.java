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

    /**
     * Constructor of the complex class.
     *
     * @param real the real value of the complex number
     * @param imag the imaginary value of the complex number
     *
     */
    public Complex(double real, double imag) {
        this.imag = imag;
        this.real = real;

    }

    /**
     * Return the magnitude of the complex number
     *
     * @return the magnitude of the complex number
     */
    public double magnitude() {
        return Math.sqrt(real * real + imag * imag);
    }

    /**
     * Get the real value of the complex number
     *
     * @return real value of the complex
     */
    public double getReal() {
        return real;
    }

    /**
     * Get the imaginary value of the complex number
     *
     * @return
     */
    public double getImaginary() {
        return imag;
    }

    /**
     * Perfrom a add operation of two complex numbers; Resturn the result as a
     * new Complex number
     *
     * @param a another complex number that to be added
     * @return a new complex number as the result of the add operation.
     */
    public Complex add(Complex a) {
        double real = this.real + a.getReal();
        double imag = this.imag + a.getImaginary();
        return new Complex(real, imag);
    }

    /**
     * Perfrom a substract operation of two complex numbers; Resturn the result
     * as a new Complex number
     *
     * @param a another complex number to be substracted
     * @return a new complex number as the result of the substract operation.
     */
    public Complex sub(Complex a) {
        return this.add(a.negate());
    }

    /**
     * Perfrom a negate operation of two complex numbers; Resturn the result as
     * a new Complex number
     *
     * @return a new complex number as the result of the negate operation.
     */
    public Complex negate() {
        return new Complex(-this.real, -this.imag);
    }

    /**
     * Perfrom a multiplication operation of two complex numbers; Resturn the
     * result as a new Complex number
     *
     * @param a the Complex multiplier
     * @return a new complex number as the result of the substract operation.
     */
    public Complex mul(Complex a) {
        double real = this.real * a.getReal() - this.imag * a.getImaginary();
        double imag = this.real * a.getImaginary() + this.imag * a.getReal();
        return new Complex(real, imag);
    }

    /**
     * Perfrom a multiplication operation of a complex number with a real
     * number; Resturn the result as a new Complex number
     *
     * @param a the real multiplier
     * @return a new complex number as the result of the substract operation.
     */
    public Complex mul(double a) {
        double real = this.real * a;
        double imag = this.imag * a;
        return new Complex(real, imag);
    }

    /**
     * Give the result of e to the power of the complex number
     *
     * @return e^(this)
     */
    public Complex exp() {
        return new Complex(Math.exp(real) * Math.cos(this.imag), Math.exp(real) * Math.sin(this.imag));
    }

    /**
     * Return true if the two complex numbers have the same real and imagine
     * value
     *
     * @param obj another object to be compared with.
     * @return True if the two objects are the same.
     */
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
