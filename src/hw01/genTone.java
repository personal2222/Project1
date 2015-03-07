/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Ruby
 */
public class genTone {

    enum ToneType {

        SINE, SQUARE, SAWTOOTH
    }

    private static final double stdFreq = 44100;
    private static final AudioFormat toneAudioFormat = new AudioFormat(
            PCM_SIGNED, 44100, 8, 1, 4, 44100, false);

    public static Sound generatePureToneAsSound(double freq, double amplitude, double duration, ToneType toneType) throws UnsupportedAudioFileException {
        byte[] rawWave = genTone.generatePureTone(freq, amplitude, duration, toneType);
        return genTone.translateToSound(rawWave);
    }

    public static byte[] generatePureTone(double freq, double amplitude, double duration, ToneType toneType) {
//        if (amplitude > 1 || amplitude < 0) {
//            throw new AmplitudeOutOfRangeException("Invalid input for amplitude, legal amplitude should be from 0 to 1");
//        }
        if (toneType == ToneType.SINE) {
            return gennToneSin(freq, amplitude, duration);
        } else if (toneType == ToneType.SAWTOOTH) {
            return gennToneSaw(freq, amplitude, duration);
        } else {
            return gennToneSquare(freq, amplitude, duration);
        }
    }

    public static Sound translateToSound(byte[] a) throws UnsupportedAudioFileException {

        ByteBuffer buffer = ByteBuffer.wrap(a);
        return new Sound(buffer.asShortBuffer(), toneAudioFormat);
    }

    private static byte[] gennToneSquare(double freq, double amplitude, double duration) {
        int totSlot = (int) (duration * genTone.stdFreq);
        int slotsPerWave = (int) (genTone.stdFreq / freq);
        byte actualAmplitude = (byte) (amplitude * Byte.MAX_VALUE / 2);
        byte[] generatedWave = new byte[totSlot];
        for (int i = 0; i < (generatedWave.length); ++i) {
            if ((i % slotsPerWave) < (slotsPerWave / (2.0))) {
                generatedWave[i] = (byte) (-actualAmplitude);
            } else {
                generatedWave[i] = actualAmplitude;
            }
        }
        return generatedWave;
    }

    private static byte[] gennToneSaw(double freq, double amplitude, double duration) {
        int totSlot = (int) (duration * genTone.stdFreq);
        int slotsPerWave = (int) (genTone.stdFreq / freq);
        int halfWave = slotsPerWave / 2;
        byte actualAmplitude = (byte) (amplitude * Byte.MAX_VALUE / 2);
        byte ampIncreasePerSlot = (byte) (2 * actualAmplitude / halfWave);
        byte[] generatedWave = new byte[totSlot];
        for (int i = 0; i < (generatedWave.length); ++i) {
            int indexInHalfWave = i % halfWave;
            int indexOfHalfWave = i / halfWave;
            if (((indexInHalfWave) < (halfWave / (2.0))) && indexOfHalfWave % 2 == 0) {
                generatedWave[i] = (byte) (-actualAmplitude + indexInHalfWave * ampIncreasePerSlot);
            } else {
                generatedWave[i] = (byte) (actualAmplitude - indexInHalfWave * ampIncreasePerSlot);
            }
        }
        return generatedWave;
    }

    private static byte[] gennToneSin(double freq, double amplitude, double duration) {
        //freq = freq / 2;

        int totSlot = (int) (duration * genTone.stdFreq);
        byte actualAmplitude = (byte) (amplitude * Byte.MAX_VALUE / 2);
        byte[] generatedWave = new byte[totSlot];
        for (int i = 0; i < (generatedWave.length); ++i) {
            generatedWave[i] = (byte) (actualAmplitude * Math.sin(2 * Math.PI * freq * i / genTone.stdFreq));
        }
        return generatedWave;
    }

}

//class AmplitudeOutOfRangeException extends Exception {
//
//    public AmplitudeOutOfRangeException(String message) {
//        super(message);
//    }
//
//}
