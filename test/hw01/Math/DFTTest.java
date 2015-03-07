/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01.Math;

import org.junit.After;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Borrowed many ideas from the following website
 *
 * @see
 * http://stackoverflow.com/questions/25298119/unit-testing-a-discrete-fourier-transformation
 * @author Ruby
 */
public class DFTTest {

    public DFTTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of Transform method, of class DFT.
     */
    @Test
    public void testTransform() {
        System.out.println("Transform");
        Complex[] series = new Complex[16];
        for (int i = 0; i < 16; ++i) {
            series[i] = new Complex(0, 0);
        }
        Complex[] expResult = series;
        Complex[] result = DFT.Transform(series);
        assertArrayEquals(expResult, result);
        for (int i = 0; i < 16; ++i) {
            series[i] = new Complex(0, 8 * 2 * Math.PI * i / 16).exp();
            expResult[i] = new Complex(0, 0);
        }

        result = DFT.Transform(series);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of FTransform method, of class DFT.
     */
    @Test
    public void testFTransform() throws Exception {
        System.out.println("FTransform");
        Complex[] series = new Complex[16];
        for (int i = 0; i < 16; ++i) {
            series[i] = new Complex(0, 0);
        }
        Complex[] expResult = series;
        Complex[] result = DFT.FTransform(series);
        assertArrayEquals(expResult, result);
        for (int i = 0; i < 16; ++i) {
            series[i] = new Complex(0, 8 * 2 * Math.PI * i / 16).exp();
            expResult[i] = new Complex(0, 0);
        }

        result = DFT.FTransform(series);
        assertArrayEquals(expResult, result);
    }

    @Test(expected = LengthNotAPowerOfTwoException.class)
    public void lengthExceptionTest() throws LengthNotAPowerOfTwoException {
        Complex[] series = new Complex[3];
        series[0] = new Complex(0, 0);
        series[1] = new Complex(0, 0);
        series[2] = new Complex(0, 0);
        DFT.FTransform(series);
    }

}
