/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

/**
 *
 * @author Ruby
 */
public class genTone {

    enum ToneType {

        SINE, SQUARE, TRIANGLE, SAWTOOTH
    }

    private static final double stdFreq = 44100;

//    public static short[] generatePureTone(double freq, double amplitude, double duration, ToneType toneType) {
//
//    }
    public static short[] gennToneSquare(int freq, double amplitude, double duration) {
        freq = freq / 2;
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

    public static short[] gennToneSaw(int freq, double amplitude, double duration) {
        freq = freq / 2;
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

    public static byte[] gennToneSin(int freq, double amplitude, double duration) {
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
