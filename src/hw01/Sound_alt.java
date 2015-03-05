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

    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException, MalformedURLException, InterruptedException {
        File file = new File("./src/hw01/Jump.wav");
        Sound_alt sound = new Sound_alt(file);
        sound.play();
        Thread.sleep(1000);

//        byte[] rawdata = sound.readRawWav(file);
//        sound.write("./src/hw01/copypaster.wav", sound.readInput(file));
//
//        AudioFormat audioFormat = AudioSystem.getAudioFileFormat(file.toURI().toURL()).getFormat();
//        sound.write("./src/hw01/testEcho.wav", sound.echo(100, 1.0, rawdata), audioFormat);
//        //sound.write("./src/hw01/testEcho.wav", this.echo(1000, 0.6, rawdata), audioFormat);
    }

    private Mixer mixer;
    private Clip clip;
    private Mixer.Info[] mixInfos;
    private File audioFile;

    public Sound_alt(File file) {
        mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        audioFile = file;
    }

    public byte[] echo(int delayInMiSec, double decay, byte[] rawWav) {
        int sampleDelay = (int) 44.100 * delayInMiSec * 2;
        for (int i = 0; i < rawWav.length - sampleDelay; i++) {
            rawWav[i + sampleDelay] += (byte) ((float) rawWav[i] * decay);
        }
        return rawWav;
    }

    public byte[] echo(int delayInMiSec, double decay) throws IOException, UnsupportedAudioFileException {
        byte[] rawWav = this.readRawWav();
        int sampleDelay = (int) 44.100 * delayInMiSec * 2;
        for (int i = 0; i < rawWav.length - sampleDelay; i++) {
            rawWav[i + sampleDelay] += (byte) ((float) rawWav[i] * decay);
        }
        return rawWav;
    }

    public void play() throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.start();
        Thread.sleep(1000);

    }

    public AudioInputStream readInput(File audioFile) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
    }

    public byte[] readRawWav(AudioInputStream audioStream) throws IOException {
        byte[] rawWave;
        int byteLength = (int) audioStream.getFrameLength() * 2 + 1;
        rawWave = new byte[byteLength];
        audioStream.read(rawWave);
        return rawWave;
    }

    public byte[] readRawWav(File audioFile) throws IOException, UnsupportedAudioFileException {
        return readRawWav(readInput(audioFile));
    }

    public byte[] readRawWav() throws IOException, UnsupportedAudioFileException {
        return this.readRawWav(this.audioFile);
    }

    public void write(String outputFilePath, byte[] rawWave, AudioFormat audioFormat) throws IOException {
        File outputFile = new File(outputFilePath);
        int byteLength = rawWave.length;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(rawWave);
        AudioInputStream out = new AudioInputStream(byteStream, audioFormat, byteLength * 2 + 1);
        AudioSystem.write(out, AudioFileFormat.Type.WAVE, outputFile);
        out.close();
        byteStream.close();
    }

    public void write(String outputFilePath) throws IOException, UnsupportedAudioFileException {
        byte[] rawWave = this.readRawWav();
        AudioFormat audioFormat = AudioSystem.getAudioFileFormat(this.audioFile.toURI().toURL()).getFormat();
        this.write(outputFilePath, rawWave, audioFormat);
    }

    public void write(String outputFilePath, AudioInputStream audioStream) throws IOException {
        byte[] rawWave = this.readRawWav(audioStream);
        AudioFormat audioFormat = audioStream.getFormat();
        this.write(outputFilePath, rawWave, audioFormat);
    }

}
