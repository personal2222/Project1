/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author huangjiayu
 */
public class SoundIOTest {

    public SoundIOTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of read method, of class SoundIO.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        String Filename = "./src/hw01/drum.wav";
        File file = new File(Filename);
        Sound expResult = null;
        Sound instance = SoundIO.read(Filename);
        AudioInputStream expected = AudioSystem.getAudioInputStream(file.toURI().toURL());
        AudioInputStream result = instance.getAIS();

        Assert.assertEquals(expected.getFrameLength(), result.getFrameLength());
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of write method, of class SoundIO.
     */
    @Test
    public void testWrite_Sound_File() throws Exception {
        System.out.println("write");
        Sound s = null;
        File f = null;
        SoundIO.write(s, f);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class SoundIO.
     */
    @Test
    public void testWrite_Sound_String() throws Exception {
        System.out.println("write");
        Sound s = null;
        String filePath = "";
        SoundIO.write(s, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeBitLength method, of class SoundIO.
     */
    @Test
    public void testChangeBitLength() {
        System.out.println("changeBitLength");
        AudioFormat f = null;
        int bitlength = 0;
        AudioFormat expResult = null;
        AudioFormat result = SoundIO.changeBitLength(f, bitlength);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of downSampleRateby2 method, of class SoundIO.
     */
    @Test
    public void testDownSampleRateby2() {
        System.out.println("downSampleRateby2");
        AudioFormat f = null;
        AudioFormat expResult = null;
        AudioFormat result = SoundIO.downSampleRateby2(f);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
