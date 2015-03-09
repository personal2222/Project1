/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Zhengri Fan, Jiayu Huang
 * Date: 2015-2-28
 * Time: 22:24:45
 *
 * Project: csci205
 * Package: hw01
 * File: DFT
 * Description: Project1
 *
 * ****************************************
 */
package hw01;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
 * @author Jiayu Huang, Zhengri Fan
 */
/**
 * We get some basic ideas from the following video to use the java sound api.
 *
 * @see
 * <a href="https://www.youtube.com/watch?v=nUKya2DvYSo">
 * https://www.youtube.com/watch?v=nUKya2DvYSo</a>
 * @param fileName
 */
public class Sound {

    private ShortBuffer s;
    private AudioFormat af;

    /**
     * The constructor of the Sound object. It takes a ShortBuffer that
     * represents the wave of the sound, and the audioFormat of that wave.
     *
     * @param b the wave of the sound
     * @param af the audioformat of the sound
     * @throws UnsupportedAudioFileException
     */
    public Sound(ShortBuffer b, AudioFormat af) throws UnsupportedAudioFileException {

        this.s = b;
        this.af = af;

    }

    /**
     * The alternative constructor of the sound object, takes in a path
     * representing the sound file.
     *
     * @param filepath the path representing the audio file.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Sound(String filepath) throws UnsupportedAudioFileException, IOException {
        Sound a = SoundIO.read(filepath);
        this.af = a.getAf();
        this.s = a.getS();
    }

    /**
     * Set the shortBuffer in the sound object
     *
     * @param s the new short buffer
     */
    public void setS(ShortBuffer s) {
        this.s = s;
    }

    /**
     * Set the audioformat in the sound object
     *
     * @param af the new audioformat of the Sound object
     */
    public void setAf(AudioFormat af) {
        this.af = af;
    }

    /**
     * Get the shortBuffer of the sound object
     *
     * @return the shortBuffer of the sound object
     */
    public ShortBuffer getS() {
        return s;
    }

    /**
     * Get the audioformat of the sound object
     *
     * @return the audioformat of the sound object
     */
    public AudioFormat getAf() {
        return af;
    }

    /**
     * Play the given sound with a given play time
     *
     * @param playtime the time for the sound to be played
     * @throws LineUnavailableException
     * @throws IOException
     * @throws InterruptedException
     */
    public void play(double playtime) throws LineUnavailableException, IOException, InterruptedException {

        try (AudioInputStream out = this.getAIS()) {
            Clip clip = AudioSystem.getClip();
            clip.open(out);
            clip.start();
            Thread.sleep((long) (playtime * 1000));
            clip.stop();
            clip.close();
            out.close();
        }

    }

    /**
     * Play the full sound
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws LineUnavailableException
     */
    public void play() throws IOException, InterruptedException, LineUnavailableException {

        try (AudioInputStream out = this.getAIS()) {
            Clip clip = AudioSystem.getClip();
            clip.open(out);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.stop();
            clip.close();
            out.close();
        }

    }

    /**
     * Opens an audioInputStream for the given sound object
     *
     * @see
     * <a href="http://stackoverflow.com/questions/11665147/convert-a-longbuffer-intbuffer-shortbuffer-to-bytebuffer">
     * http://stackoverflow.com/questions/11665147/convert-a-longbuffer-intbuffer-shortbuffer-to-bytebuffer</a>
     * Borrowed ideas for converting buffers
     * @return the audioInputStream of the sound object
     * @throws IOException
     */
    public AudioInputStream getAIS() throws IOException {
        short[] buf = new short[this.s.limit()];
        this.s.get(buf);
        this.s = ShortBuffer.wrap(buf);
        int byteLength = buf.length * 2;
        byte[] in = new byte[byteLength];
        ByteBuffer.wrap(in).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buf);
        AudioInputStream out;
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(in)) {
            out = new AudioInputStream(byteStream, this.af, byteLength);
        }

        return out;
    }

    /**
     * Retrive the sort array buffered by the shortbuffer
     *
     * @return a short array of the raw wave.
     */
    public short[] getShortRepresentation() {
        short[] dst = new short[this.s.limit()];
        for (int i = 0; i < dst.length; ++i) {
            dst[i] = this.s.get(i);
        }
        return dst;
    }

    /**
     * Down the sample rate by two
     *
     * @return a new Sound that is almost the same as pervious except the sample
     * rate was one half of before
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
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

    /**
     * Make an echo of the current sound and return a new sound representing it.
     *
     * @param delayInMiSec The delay in ms
     * @param decay 0 to 1 representing the percentage of the amplitude of the
     * echo to the original sound. 1 represents 100%
     * @return A new Sound with echo
     * @throws UnsupportedAudioFileException
     */
    public Sound echo(int delayInMiSec, double decay) throws UnsupportedAudioFileException {
        int sampleDelay = (int) ((double) this.af.getSampleRate() * (float) delayInMiSec);
        short buf = 0;
        Sound a = this.SetVolumn(-0.5);//This is to avoid noise
        short[] buffer = a.getShortRepresentation();
        for (int i = 0; i < buffer.length - sampleDelay; i++) {
            buffer[i + sampleDelay] += buffer[i] * decay;
        }
        ShortBuffer b = ShortBuffer.wrap(buffer);
        return new Sound(b, this.af);
    }

    /**
     * Set the volume of the sound by the ratio represented by var set.
     *
     *
     * @param set if positive, increse the volume by set * 100%; otherwise
     * decrease the volume by abs(set) * 100 %
     * @return A new Sound representing the original sound after the volume
     * adjustment.
     * @throws UnsupportedAudioFileException
     */
    public Sound SetVolumn(double set) throws UnsupportedAudioFileException {
        double ratio = 1 + set;
        short[] buffer = this.getShortRepresentation();
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (short) (buffer[i] * ratio);
        }

        Sound rtn = new Sound(ShortBuffer.wrap(buffer), this.af);
        if (rtn.getMaxVolume() >= Short.MAX_VALUE) {
            return this;
        } else {
            return rtn;
        }
    }

    /**
     * Set the volume of the current Sound object by the number indicated by
     * set.
     *
     * @param set if positive, increse the volume by set * 100%; otherwise
     * decrease the volume by abs(set) * 100 %
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public void SetthisVolumn(double set) throws UnsupportedAudioFileException, IOException {
        Sound temp = this.SetVolumn(set);
        this.s = temp.s;
    }

    /**
     * Add a mistery reverberation to the current sound
     *
     * @return A new Sound representing the original sound with reverberation.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Sound Reverberation() throws UnsupportedAudioFileException, IOException {
        Sound raw2 = this;
        Sound raw1 = this;
        Sound result;

        result = raw2.addSound(raw1.echo(700, 0.03));
        result = result.addSound(raw1.echo(600, 0.05));
        result = result.addSound(raw1.echo(500, 0.10));
        result = result.addSound(raw1.echo(400, 0.15));
        result = result.addSound(raw1.echo(300, 0.20));
        result = result.addSound(raw1.echo(200, 0.25));
        result = result.addSound(raw1.echo(100, 0.30));
        result = result.addSound(raw1.echo(750, 0.01));
        result = result.addSound(raw1.echo(650, 0.03));
        result = result.addSound(raw1.echo(550, 0.07));
        result = result.addSound(raw1.echo(450, 0.12));
        result = result.addSound(raw1.echo(350, 0.17));
        result = result.addSound(raw1.echo(250, 0.23));
        result = result.addSound(raw1.echo(150, 0.27));

        return result;
    }

    /**
     * Add two sound objects to each other. Mainly by adding their waves
     * together.
     *
     * @param b Another sound Object to be added with the current sound object
     * @return A new Sound object representing the sum of the two object
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
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

    /**
     * Get the max volume possible
     *
     * @return a short number representing the maxium absolute value of the
     * sound wave.
     */
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
