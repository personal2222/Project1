/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Zhengri Fan
 */
public class SoundTest {

    public SoundTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setS method, of class Sound.
     */
    @Test
    public void testSetS() {
        System.out.println("setS");
        ShortBuffer s = null;
        Sound instance = null;
        instance.setS(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAf method, of class Sound.
     */
    @Test
    public void testSetAf() {
        System.out.println("setAf");
        AudioFormat af = null;
        Sound instance = null;
        instance.setAf(af);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getS method, of class Sound.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        Sound instance = null;
        ShortBuffer expResult = null;
        ShortBuffer result = instance.getS();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAf method, of class Sound.
     */
    @Test
    public void testGetAf() {
        System.out.println("getAf");
        Sound instance = null;
        AudioFormat expResult = null;
        AudioFormat result = instance.getAf();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of play method, of class Sound.
     */
    @Test
    public void testPlay_long() throws Exception {
        System.out.println("play");
        long playtime = 0L;
        Sound instance = null;
        instance.play(playtime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of play method, of class Sound.
     */
    @Test
    public void testPlay_0args() throws Exception {
        System.out.println("play");
        Sound instance = null;
        instance.play();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAIS method, of class Sound.
     */
    @Test
    public void testGetAIS() throws Exception {
        System.out.println("getAIS");
        Sound instance = null;
        AudioInputStream expResult = null;
        AudioInputStream result = instance.getAIS();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getShortRepresentation method, of class Sound.
     */
    @Test
    public void testGetShortRepresentation() {
        System.out.println("getShortRepresentation");
        Sound instance = null;
        short[] expResult = null;
        short[] result = instance.getShortRepresentation();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of downSamplebytwo method, of class Sound.
     */
    @Test
    public void testDownSamplebytwo() throws Exception {
        System.out.println("downSamplebytwo");
        Sound instance = null;
        Sound expResult = null;
        Sound result = instance.downSamplebytwo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of echo method, of class Sound.
     */
    @Test
    public void testEcho() throws Exception {
        System.out.println("echo");
        int delayInMiSec = 0;
        double decay = 0.0;
        Sound instance = null;
        Sound expResult = null;
        Sound result = instance.echo(delayInMiSec, decay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetVolumn method, of class Sound.
     */
    @Test
    public void testSetVolumn() throws Exception {
        System.out.println("SetVolumn");
        double set = 0.0;
        Sound instance = null;
        Sound expResult = null;
        Sound result = instance.SetVolumn(set);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SetthisVolumn method, of class Sound.
     */
    @Test
    public void testSetthisVolumn() throws Exception {
        System.out.println("SetthisVolumn");
        double set = 0.0;
        Sound instance = null;
        instance.SetthisVolumn(set);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Reverberation method, of class Sound.
     */
    @Test
    public void testReverberation() throws Exception {
        System.out.println("Reverberation");
        Sound instance = null;
        Sound expResult = null;
        Sound result = instance.Reverberation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSound method, of class Sound.
     */
    @Test
    public void testAddSound() throws Exception {
        System.out.println("addSound");
        Sound b = null;
        Sound instance = null;
        Sound expResult = null;
        Sound result = instance.addSound(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxVolume method, of class Sound.
     */
    @Test
    public void testGetMaxVolume() {
        System.out.println("getMaxVolume");
        Sound instance = null;
        short expResult = 0;
        short result = instance.getMaxVolume();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
