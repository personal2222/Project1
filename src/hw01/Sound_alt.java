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
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Ruby
 *
 *
 *
 * http://www.bfxr.net/
 */
public class Sound_alt {

    private Mixer mixer;
    private Clip clip;
    private Mixer.Info[] mixInfos;
    private File audioFile;

    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException, MalformedURLException, InterruptedException {
        File file = new File("./src/hw01/Jump2.wav");
        Sound_alt sound = new Sound_alt(file);
        sound.play();
        ShortBuffer rawdata = sound.readRawWav(file);
        sound.write("./src/hw01/copypaster.wav", sound.readInput(file));
        AudioFormat audioFormat = AudioSystem.getAudioFileFormat(file.toURI().toURL()).getFormat();
        sound.write("./src/hw01/testEcho.wav", sound.echo(100, 0.3, rawdata), audioFormat);

        //sound.write("./src/hw01/testEcho.wav", this.echo(1000, 0.6, rawdata), audioFormat);
    }

    public Sound_alt(File file) {
        mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        audioFile = file;
    }

    public void play() throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        clip.start();

    }

    public void write(String outputFilePath, AudioInputStream audioStream) throws IOException {
        File outputFile = new File(outputFilePath);

        ShortBuffer rawWave = this.readRawWav(audioStream);
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

    public void write(String outputFilePath, ShortBuffer rawWave, AudioFormat audioFormat) throws IOException {
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

    public ShortBuffer readRawWav(AudioInputStream audioStream) throws IOException {
        byte[] rawWave;
        int byteLength = (int) audioStream.getFrameLength() * 4 + 1;
        rawWave = new byte[byteLength];
        audioStream.read(rawWave);
        ByteBuffer a = ByteBuffer.wrap(rawWave);
        ShortBuffer b = a.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        return b;
    }

    public ShortBuffer readRawWav(File audioFile) throws IOException, UnsupportedAudioFileException {
        return readRawWav(readInput(audioFile));
    }

    public AudioInputStream readInput(File audioFile) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
    }

    public ShortBuffer readRawWav() throws IOException, UnsupportedAudioFileException {
        return this.readRawWav(this.audioFile);
    }

    public ShortBuffer echo(int delayInMiSec, double decay, ShortBuffer rawWav) {
        int sampleDelay = (int) 44.100 * delayInMiSec;
        short buf = 0;
        int limit = findlimit(rawWav);
        for (int i = 0; i < rawWav.limit() - sampleDelay; i++) {
            buf = rawWav.get(i + sampleDelay);
            buf += (short) ((float) rawWav.get(i) * decay);
            if (buf >= limit) {
                buf = (short) limit;
            }
            rawWav.put(i + sampleDelay, buf);
        }
        return rawWav;
    }

    public int findlimit(ShortBuffer rawWav) {
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

}
