/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author huangjiayu
 */
public class WaveManager {

    public static void write(String outputFilePath, ShortBuffer rawWave, AudioFormat audioFormat) throws IOException {
        File outputFile = new File(outputFilePath);
        short[] buf = new short[rawWave.remaining()];
        rawWave.get(buf);
        int byteLength = rawWave.limit() * 2;
        byte[] in = new byte[byteLength];
        ByteBuffer.wrap(in).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buf);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(in);
        AudioInputStream out = new AudioInputStream(byteStream, audioFormat, byteLength);
        AudioSystem.write(out, AudioFileFormat.Type.WAVE, outputFile);
        out.close();
        byteStream.close();
    }

    public static void write(String outputFilePath, AudioInputStream audioStream) throws IOException {
        File outputFile = new File(outputFilePath);
        ShortBuffer rawWave = WaveManager.readRawWav(audioStream);
        short[] buf = new short[rawWave.remaining()];
        rawWave.get(buf);
        int byteLength = buf.length * 2;
        AudioFormat audioFormat = audioStream.getFormat();
        byte[] in = new byte[byteLength];
        ByteBuffer.wrap(in).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buf);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(in);
        AudioInputStream out = new AudioInputStream(byteStream, audioFormat, byteLength);
        AudioSystem.write(out, AudioFileFormat.Type.WAVE, outputFile);
        out.close();
        byteStream.close();
    }

    public static ShortBuffer readRawWav(AudioInputStream audioStream) throws IOException {
        byte[] rawWave;
        int byteLength = (int) audioStream.getFrameLength() * 4 + 1;
        rawWave = new byte[byteLength];
        audioStream.read(rawWave);
        ByteBuffer a = ByteBuffer.wrap(rawWave);
        ShortBuffer b = a.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        return b;
    }

    public static ShortBuffer readRawWav(File audioFile) throws IOException, UnsupportedAudioFileException {
        return readRawWav(readInput(audioFile));
    }

    public static AudioInputStream readInput(File audioFile) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
    }

    public static AudioInputStream readInput(String filepath) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        File f = new File(filepath);
        return readInput(f);
    }

    public static void play(AudioInputStream a) throws LineUnavailableException, IOException, InterruptedException {
        Clip clip = AudioSystem.getClip();
        clip.open(a);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
        clip.stop();

    }

    public static int findlimit(ShortBuffer rawWav) {
        short max = 0;
        short min = 0;
        for (int i = 0; i < rawWav.limit(); i++) {
            if (rawWav.get(i) > max) {
                max = rawWav.get(i);
            }
            if (rawWav.get(i) < min) {
                min = rawWav.get(i);
            }
        }
        if (min * -1 > max) {
            return min * -1;
        } else {
            return max;
        }
    }

    public static AudioFormat getformat(File f) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioFileFormat(f.toURI().toURL()).getFormat();
    }

    public static short[] generateaDuplicate(ShortBuffer a) {
        short buf = 0;
        short[] buffer = new short[a.limit()];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (a.get(i));
        }
        return buffer;
    }

}
