/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Zhengri Fan, Jiayu Huang
 * Date: 2015-3-3
 * Time: 19:22:13
 *
 * Project: csci205
 * Package: hw01
 * File: SoundIO
 * Description: Project1
 *
 * ****************************************
 */
package hw01;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.Arrays;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Jiayu Huang, Zhengri Fan
 */
public class SoundIO {

    /**
     * Read the given sound file and return a Sound object representing it
     *
     * @param Filename, the path of the sound object to be opened
     * @return A sound object representing the given audio file
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @see
     * <a href="http://stackoverflow.com/questions/10804852/how-to-convert-short-array-to-byte-array">
     * http://stackoverflow.com/questions/10804852/how-to-convert-short-array-to-byte-array</a>
     */
    public static Sound read(String Filename) throws IOException, UnsupportedAudioFileException {
        File audioFile = new File(Filename);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
        byte[] rawWave;
        int byteLength = (int) audioStream.getFrameLength() * audioStream.getFormat().getFrameSize();
        rawWave = new byte[byteLength];
        audioStream.read(rawWave);
        AudioFormat soundFormat = audioStream.getFormat();
        rawWave = cutZerosWhenRead(rawWave, soundFormat);
        ByteBuffer a = ByteBuffer.wrap(rawWave);
        ShortBuffer b = a.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        audioStream.close();
        return new Sound(b, soundFormat);
    }

    /**
     * Helper method to cut zeros when reading an audioFile.
     *
     *
     * @param rawWave
     * @param soundFormat
     * @return
     */
    private static byte[] cutZerosWhenRead(byte[] rawWave, AudioFormat soundFormat) {
        int zeroIdx = 0;
        int zeroCounting = 0;
        boolean COUNTING = false;
        for (int i = 0; i < rawWave.length; ++i) {
            if (rawWave[i] == 0 && COUNTING) {
                zeroCounting += 1;
            } else if (rawWave[i] == 0 && !COUNTING) {
                COUNTING = true;
                zeroIdx = i;
                zeroCounting = 1;
            } else if (rawWave[i] != 0 && COUNTING) {
                COUNTING = false;
            }
            if (zeroCounting == (int) (soundFormat.getSampleRate() / 2)) {
                break;
            }
        }
        byte[] sliced = new byte[zeroIdx];
        sliced = Arrays.copyOfRange(rawWave, 0, zeroIdx);
        return sliced;
    }

    /**
     * Write the given Sound object to the file specified.
     *
     * @param s
     * @param f
     * @throws IOException
     */
    public static void write(Sound s, File f) throws IOException {
        AudioInputStream ais = s.getAIS();
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, f);
        ais.close();
    }

    /**
     * Write the given Sound object to the path specified.
     *
     * @param s
     * @param f
     * @throws IOException
     */
    public static void write(Sound s, String filePath) throws IOException {
        File f = new File(filePath);
        SoundIO.write(s, f);
    }

    /**
     * Change the bit length and return the new AudioFormat
     *
     * @param f
     * @param bitlength
     * @return
     */
    public static AudioFormat changeBitLength(AudioFormat f, int bitlength) {
        return new AudioFormat(
                f.getEncoding(),
                f.getSampleRate(),
                bitlength,
                f.getChannels(),
                f.getFrameSize() * (f.getSampleSizeInBits() / bitlength),
                f.getFrameRate(),
                f.isBigEndian()
        );

    }

    /**
     * Downsize the sample rate by 2
     *
     * @param f the original audioformat
     * @return the audioformat representing the new format.
     */
    public static AudioFormat downSampleRateby2(AudioFormat f) {
        return new AudioFormat(
                f.getEncoding(),
                f.getSampleRate() / 2,
                f.getSampleSizeInBits(),
                f.getChannels(),
                f.getFrameSize() / 2,
                f.getFrameRate(),
                f.isBigEndian()
        );
    }

}
