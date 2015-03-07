/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author zf002
 */
public class Sound {

    private ShortBuffer s;
    private AudioFormat af;

    /**
     * https://www.youtube.com/watch?v=nUKya2DvYSo
     *
     * @param fileName
     */
    public Sound(ShortBuffer b, AudioFormat af) throws MalformedURLException, UnsupportedAudioFileException, IOException {

        this.s = b;
        this.af = af;

    }

    public Sound(String filepath) throws MalformedURLException, UnsupportedAudioFileException, IOException {
        Sound a = SoundIO.read(filepath);
        this.af = a.getAf();
        this.s = a.getS();
    }

    public void setS(ShortBuffer s) {
        this.s = s;
    }

    public void setAf(AudioFormat af) {
        this.af = af;
    }

    public ShortBuffer getS() {
        return s;
    }

    public AudioFormat getAf() {
        return af;
    }

    public void play() throws LineUnavailableException, IOException, InterruptedException {

        AudioInputStream out = this.getAIS();
        Clip clip = AudioSystem.getClip();
        clip.open(out);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
        clip.stop();
        out.close();

    }

    public AudioInputStream getAIS() throws IOException {
        short[] buf = new short[this.s.limit()];
        this.s.get(buf);
        this.s = ShortBuffer.wrap(buf);
        int byteLength = buf.length * 2;
        byte[] in = new byte[byteLength];
        ByteBuffer.wrap(in).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buf);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(in);
        AudioInputStream out = new AudioInputStream(byteStream, this.af, byteLength);
        byteStream.close();
        return out;
    }

    public short[] getShortRepresentation() {
        short[] dst = new short[this.s.limit()];
        this.s.get(dst);
        this.s = ShortBuffer.wrap(dst);
        return dst;
    }

    public Sound downSamplebytwo() throws UnsupportedAudioFileException, IOException {
        short[] buffer = this.getShortRepresentation();
        short[] returnValue = new short[buffer.length / 2];
        for (int i = 0; i < (buffer.length); i++) {
            if (i % 2 == 0) {
                returnValue[i / 2] = buffer[i];
            }
        }
        ShortBuffer b = ShortBuffer.wrap(returnValue);
        return new Sound(b, SoundIO.downSampleRateby2(this.af));
    }

    public Sound echo(int delayInMiSec, double decay) throws UnsupportedAudioFileException, IOException {
        int sampleDelay = (int) (44.100 * (float) delayInMiSec);
        short buf = 0;
        Sound a = this.SetVolumn(-0.5);//This is to avoid noise
        short buffer[] = a.getShortRepresentation();
        for (int i = 0; i < buffer.length - sampleDelay; i++) {

            buffer[i + sampleDelay] += buffer[i] * decay;
        }

        return new Sound(ShortBuffer.wrap(buffer), this.af);
    }

    public Sound SetVolumn(double set) throws UnsupportedAudioFileException, IOException {
        double ratio = 1 + set;
        short buffer[] = this.getShortRepresentation();
        for (int i = 0; i < buffer.length; i++) {

            buffer[i] = (short) (buffer[i] * ratio);
        }
        return new Sound(ShortBuffer.wrap(buffer), this.af);
    }

    public Sound Reverberation() throws UnsupportedAudioFileException, IOException {
        Sound raw2 = this;
        Sound raw1 = this;

        raw2.addSound(raw1.echo(600, 0.03));
        raw2.addSound(raw1.echo(500, 0.05));
        raw2.addSound(raw1.echo(400, 0.10));
        raw2.addSound(raw1.echo(200, 0.15));
        raw2.addSound(raw1.echo(300, 0.20));
        raw2.addSound(raw1.echo(200, 0.25));
        raw2.addSound(raw1.echo(100, 0.30));

        return raw2;
    }

    public Sound addSound(Sound b) throws UnsupportedAudioFileException, IOException {
        short[] a;
        short[] c;
        if (this.getAf() != b.getAf()) {
            return this;
        } else {
            if ((b.getMaxVolume() + this.getMaxVolume()) >= Short.MAX_VALUE) {
                a = this.SetVolumn(-0.5).getShortRepresentation();
                c = b.SetVolumn(-0.5).getShortRepresentation();
            } else {
                a = this.getShortRepresentation();
                c = b.getShortRepresentation();
            }
            for (int i = 0; i < a.length; i++) {
                a[i] += c[i];

            }
            return new Sound(ShortBuffer.wrap(a), this.af);
        }

    }

    public short getMaxVolume() {
        short[] a = this.getShortRepresentation();
        short temp1 = 0;
        short temp2 = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < temp1) {
                temp1 = a[i];
            }
            if (a[i] > temp2) {
                temp2 = a[i];
            }
        }
        if (temp1 * -1 > temp2) {
            return (short) (temp1 * -1);
        } else {
            return temp2;
        }
    }
}
