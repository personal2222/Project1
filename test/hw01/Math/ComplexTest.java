/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01.Math;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ruby
 */
public class ComplexTest {

    private Complex cpx;
    private Complex cpx2;

    public ComplexTest() {
    }

    @Before
    public void setUp() {
        this.cpx = new Complex(3, 4);
        this.cpx2 = new Complex(5, 12);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of magnitude method, of class Complex.
     */
    @Test
    public void testMagnitude() {
        System.out.println("magnitude");
        double expResult = 5;
        double result = cpx.magnitude();
        double DELTA = 0.0001;
        assertEquals(expResult, result, DELTA);
    }

    /**
     * Test of add method, of class Complex.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Complex expResult = new Complex(8, 16);
        Complex result = cpx.add(cpx2);
        assertEquals(expResult, result);
    }

    /**
     * Test of sub method, of class Complex.
     */
    @Test
    public void testSub() {
        System.out.println("sub");
        Complex expResult = new Complex(2, 8);
        Complex result = cpx2.sub(cpx);
        assertEquals(expResult, result);
    }

    /**
     * Test of negate method, of class Complex.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Complex expResult = new Complex(-cpx.getReal(), -cpx.getImaginary());
        Complex result = cpx.negate();
        assertEquals(expResult, result);
    }

    /**
     * Test of mul method, of class Complex.
     */
    @Test
    public void testMul_Complex() {
        System.out.println("mul");
        Complex expResult = new Complex(-33, 56);
        Complex result = cpx.mul(cpx2);
        assertEquals(expResult, result);
    }

    /**
     * Test of mul method, of class Complex.
     */
    @Test
    public void testMul_double() {
        System.out.println("mul");
        double a = 2.0;
        Complex expResult = new Complex(6, 8);
        Complex result = cpx.mul(a);
        assertEquals(expResult, result);
    }

    /**
     * Test of exp method, of class Complex.
     */
    @Test
    public void testExp() {
        System.out.println("exp");
        Complex expResult = new Complex(Math.exp(3) * Math.cos(4), Math.exp(3) * Math.sin(4));
        Complex result = cpx.exp();
        assertEquals(expResult, result);
    }

}
