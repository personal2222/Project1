/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.After;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Zhengri Fan
 */
public class SoundTest {

    private static final AudioFormat testAudioFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 8, 8, 1, 4, 44100, false);

    private Sound s;

    public SoundTest() {
    }

    @Before
    public void setUp() throws UnsupportedAudioFileException {
        byte[] wave = {0, 0, 0, 1, 1, 1, 0, 0};
        this.s = new Sound(ByteBuffer.wrap(wave).asShortBuffer(), testAudioFormat);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getShortRepresentation method, of class Sound.
     */
    @Test
    public void testGetShortRepresentation() {
        System.out.println("getShortRepresentation");
        short[] expResult = {0, 1, 257, 0};
        short[] result = s.getShortRepresentation();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of downSamplebytwo method, of class Sound.
     */
    @Test
    public void testDownSamplebytwo() throws Exception {
        System.out.println("downSamplebytwo");
        Sound result = s.downSamplebytwo();
        short[] expResult = {0, 257};
        assertArrayEquals(expResult, result.getShortRepresentation());
    }

    /**
     * Test of echo method, of class Sound.
     */
    @Test
    public void testEcho() throws Exception {
        System.out.println("echo");
        int delayInMiSec = 250;
        double decay = 1;
        short[] expResult = {0, 0, 128, 0}; // Because we first set the volume to 1/2
        Sound result = s.echo(delayInMiSec, decay);
        assertArrayEquals(expResult, result.getShortRepresentation());
    }

    /**
     * Test of SetVolumn method, of class Sound.
     */
    @Test
    public void testSetVolumn() throws Exception {
        System.out.println("SetVolumn");
        double set = 1;
        short[] expResult = {0, 1 * 2, 257 * 2, 0};
        Sound result = s.SetVolumn(set);
        assertArrayEquals(expResult, result.getShortRepresentation());
    }

    /**
     * Test of SetthisVolumn method, of class Sound.
     */
    @Test
    public void testSetthisVolumn() throws Exception {
        System.out.println("SetthisVolumn");
        double set = 1;
        short[] expResult = {0, 1 * 2, 257 * 2, 0};
        s.SetthisVolumn(set);
        assertArrayEquals(expResult, s.getShortRepresentation());
    }

    /**
     * Test of addSound method, of class Sound.
     */
    @Test
    public void testAddSound() throws Exception {
        System.out.println("addSound");
        byte[] wave = {0, 0, 0, 1, 1, 1, 0, 0};
        Sound b = new Sound(ByteBuffer.wrap(wave).asShortBuffer(), testAudioFormat);
        Sound result = s.addSound(b);
        short[] expResult = {0, 1 * 2, 257 * 2, 0};
        assertArrayEquals(expResult, result.getShortRepresentation());
    }

    /**
     * Test of getMaxVolume method, of class Sound.
     */
    @Test
    public void testGetMaxVolume() {
        System.out.println("getMaxVolume");
        short expResult = 257;
        short result = s.getMaxVolume();
        assertEquals(expResult, result);
    }

}
