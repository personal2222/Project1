/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Zhengri Fan, Jiayu Huang
 * Date: 2015-3-3
 * Time: 22:36:49
 *
 * Project: csci205
 * Package: hw01
 * File: genTone
 * Description: Project1
 *
 * ****************************************
 */
package hw01;

import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Jiayu Huang, Zhengri Fan
 */
public class genTone {

    /**
     * Tone type enum. Contains sine, square, sawtooth.
     */
    enum ToneType {

        SINE, SQUARE, SAWTOOTH
    }
    /**
     * Standard frequency
     */
    private static final double stdFreq = 44100;
    /**
     * Default audioformat
     */
    private static final AudioFormat toneAudioFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 4, 44100, false);

    /**
     * Generate a pureTone with the given frequency, amplitude, and duration.
     *
     * @param freq
     * @param amplitude
     * @param duration
     * @param toneType
     * @return a short array representing the generated frequency
     */
    public static short[] generatePureTone(double freq, double amplitude, double duration, ToneType toneType) {
        if (toneType == ToneType.SINE) {
            return gennToneSin(freq, amplitude, duration);
        } else if (toneType == ToneType.SAWTOOTH) {
            return gennToneSaw(freq, amplitude, duration);
        } else {
            return gennToneSquare(freq, amplitude, duration);
        }
    }

    /**
     * Translate a short array to sound using the default audioformat
     *
     * @param a, a short array representing the sound
     * @return a Sound object contains the short array as the raw sound wave
     * @throws UnsupportedAudioFileException
     */
    public static Sound translateToSound(short[] a) throws UnsupportedAudioFileException {

        ShortBuffer buffer = ShortBuffer.wrap(a);
        return new Sound(buffer, toneAudioFormat);
    }

    /**
     * Generate a pureTone with the given frequency, amplitude, and duration as
     * a square wave.
     *
     * @param freq
     * @param amplitude
     * @param duration
     * @return a short array representing the generated frequency
     */
    private static short[] gennToneSquare(double freq, double amplitude, double duration) {
        int totSlot = (int) (duration * genTone.stdFreq);
        int slotsPerWave = (int) (genTone.stdFreq / freq);
        short actualAmplitude = (short) (amplitude * Short.MAX_VALUE / 2);
        short[] generatedWave = new short[totSlot];
        for (int i = 0; i < (generatedWave.length); ++i) {
            if ((i % slotsPerWave) < (slotsPerWave / (2.0))) {
                generatedWave[i] = (short) (-actualAmplitude);
            } else {
                generatedWave[i] = actualAmplitude;
            }
        }
        return generatedWave;
    }

    /**
     * Generate a pureTone with the given frequency, amplitude, and duration as
     * a sawtooth wave.
     *
     * @param freq
     * @param amplitude
     * @param duration
     * @return a short array representing the generated frequency
     */
    private static short[] gennToneSaw(double freq, double amplitude, double duration) {
        int totSlot = (int) (duration * genTone.stdFreq);
        int slotsPerWave = (int) (genTone.stdFreq / freq);
        int halfWave = slotsPerWave / 2;
        short actualAmplitude = (short) (amplitude * Short.MAX_VALUE / 2);
        short ampIncreasePerSlot = (short) (2 * actualAmplitude / halfWave);
        short[] generatedWave = new short[totSlot];
        for (int i = 0; i < (generatedWave.length); ++i) {
            int indexInHalfWave = i % halfWave;
            int indexOfHalfWave = i / halfWave;
            if (((indexInHalfWave) < (halfWave / (2.0))) && indexOfHalfWave % 2 == 0) {
                generatedWave[i] = (short) (-actualAmplitude + indexInHalfWave * ampIncreasePerSlot);
            } else {
                generatedWave[i] = (short) (actualAmplitude - indexInHalfWave * ampIncreasePerSlot);
            }
        }
        return generatedWave;
    }

    /**
     * Generate a pureTone with the given frequency, amplitude, and duration as
     * a sine wave.
     *
     * @param freq
     * @param amplitude
     * @param duration
     * @return a short array representing the generated frequency
     */
    private static short[] gennToneSin(double freq, double amplitude, double duration) {

        int totSlot = (int) (duration * genTone.stdFreq);
        short actualAmplitude = (short) (amplitude * Short.MAX_VALUE / 2);
        short[] generatedWave = new short[totSlot];
        for (int i = 0; i < (generatedWave.length); ++i) {
            generatedWave[i] = (short) (actualAmplitude * Math.sin(2 * Math.PI * freq * i / genTone.stdFreq));
        }
        return generatedWave;
    }

}
