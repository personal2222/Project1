/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Zhengri Fan
 */
public class genToneTest {

    public genToneTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of generatePureToneAsSound method, of class genTone.
     */
    @Test
    public void testGeneratePureToneAsSound() throws Exception {
        System.out.println("generatePureToneAsSound");
        double freq = 0.0;
        double amplitude = 0.0;
        double duration = 0.0;
        genTone.ToneType toneType = null;
        Sound expResult = null;
        Sound result = genTone.generatePureToneAsSound(freq, amplitude, duration, toneType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generatePureTone method, of class genTone.
     */
    @Test
    public void testGeneratePureTone() {
        System.out.println("generatePureTone");
        double freq = 0.0;
        double amplitude = 0.0;
        double duration = 0.0;
        genTone.ToneType toneType = null;
        byte[] expResult = null;
        byte[] result = genTone.generatePureTone(freq, amplitude, duration, toneType);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of translateToSound method, of class genTone.
     */
    @Test
    public void testTranslateToSound() throws Exception {
        System.out.println("translateToSound");
        byte[] a = null;
        Sound expResult = null;
        Sound result = genTone.translateToSound(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
