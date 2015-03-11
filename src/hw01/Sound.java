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
 * File: Sound
 * Description: Project1
 *
 * ****************************************
 */
package hw01;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
 * @author Jiayu Huang, Zhengri Fan
 */
/**
 * We get some basic ideas from the following video to use the java sound api.
 *
 * @see
 * <a href="https://www.youtube.com/watch?v=nUKya2DvYSo">
 * https://www.youtube.com/watch?v=nUKya2DvYSo</a>
 *
 * <a href="http://www.jsresources.org/index.html">
 * http://www.jsresources.org/index.html</a> We also got some general ideas from
 * this website
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
            Thread.sleep(clip.getMicrosecondLength() / 1000 + 1);
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
     * @see
     * <a href="http://www.jsresources.org/examples/SampleRateConverter.java.html">
     * http://www.jsresources.org/examples/SampleRateConverter.java.html</a>
     * We borrowed the code from the pervious website
     *
     * @return a new Sound that is almost the same as pervious except the sample
     * rate was one half of before
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public Sound downSamplebytwo() throws UnsupportedAudioFileException, IOException {
        float fTargetSampleRate = this.af.getSampleRate() / 2;
        File sourceFile = new File("./tempFile/soundBeforeDownRate");
        sourceFile.getParentFile().mkdirs();
        SoundIO.write(this, sourceFile);
        File targetFile = new File("./tempFile/soundAfterDownRate");
        sourceFile.getParentFile().mkdirs();

        /* We try to use the same audio file type for the target
         file as the source file. So we first have to find
         out about the source file's properties.
         */
        AudioFileFormat sourceFileFormat = AudioSystem.getAudioFileFormat(sourceFile);
        AudioFileFormat.Type targetFileType = sourceFileFormat.getType();

        /* Here, we are reading the source file.
         */
        AudioInputStream sourceStream = null;
        sourceStream = AudioSystem.getAudioInputStream(sourceFile);
        AudioFormat sourceFormat = sourceStream.getFormat();

        /* Since we now know that we are dealing with PCM, we know
         that the frame rate is the same as the sample rate.
         */
        float fTargetFrameRate = fTargetSampleRate;

        /* Here, we are constructing the desired format of the
         audio data (as the result of the conversion should be).
         We take over all values besides the sample/frame rate.
         */
        AudioFormat targetFormat = new AudioFormat(
                sourceFormat.getEncoding(),
                fTargetSampleRate,
                sourceFormat.getSampleSizeInBits(),
                sourceFormat.getChannels(),
                sourceFormat.getFrameSize(),
                fTargetFrameRate,
                sourceFormat.isBigEndian());

        /* Now, the conversion takes place.
         */
        AudioInputStream targetStream = AudioSystem.getAudioInputStream(targetFormat, sourceStream);


        /* And finally, we are trying to write the converted audio
         data to a new file.
         */
        int nWrittenBytes = 0;
        nWrittenBytes = AudioSystem.write(targetStream, targetFileType, targetFile);
        return SoundIO.read(targetFile.getPath());
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
        double delayInSec = (double) delayInMiSec / 1000.0;
        //int sampleDelay = (int) ((double) this.af.getSampleRate() * delayInSec);
        int sampleDelay = (int) (this.af.getSampleRate() * this.af.getChannels() * delayInSec);
        if (sampleDelay % 2 == 1 && this.af.getChannels() == 2) {
            sampleDelay += 1;
        }
        Sound a = null;
        try {
            a = this.SetVolumn(-0.5);//This is to avoid noise
        } catch (VolumeOutOfRangeException vore) {
            vore.printStackTrace(); // The VolumeOutOfRangeException will only thrown when try to use
            //positive numbers in set volume. So I choose to catch this exception here
            //and do nothing in order not to mess up other progrmas that calls this method.
        }
        short[] buffer = a.getShortRepresentation();
        for (int i = 0; i < buffer.length - sampleDelay; i++) {
            buffer[i + sampleDelay] += buffer[i] * decay;
            if (buffer[i + sampleDelay] >= Short.MAX_VALUE) {
                buffer[i + sampleDelay] = Short.MAX_VALUE - 200;
            }
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
     * @throws hw01.VolumeOutOfRangeException
     */
    public Sound SetVolumn(double set) throws UnsupportedAudioFileException, VolumeOutOfRangeException {
        double ratio = 1 + set;
        short[] buffer = this.getShortRepresentation();
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (short) (buffer[i] * ratio);
        }
        Sound rtn = new Sound(ShortBuffer.wrap(buffer), this.af);
        if (rtn.getMaxVolume() >= Short.MAX_VALUE) {
            throw new VolumeOutOfRangeException();
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
    public void SetthisVolumn(double set) throws UnsupportedAudioFileException, IOException, VolumeOutOfRangeException {
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
    public Sound reverberation() throws UnsupportedAudioFileException, IOException {
        Sound raw2 = new Sound(this.getS(), this.getAf());
        Sound raw1 = new Sound(this.getS(), this.getAf());

        raw2 = raw1.echo(200, 0.4);
        raw2 = raw2.echo(300, 0.3);
        raw2 = raw2.echo(400, 0.2);
        return raw2;
    }

//    /**
//     * Add a delay to a sound object
//     *
//     * @param delayInMiSec The delay in ms
//     * @param decay 0 to 1 representing the percentage of the amplitude of the
//     * echo to the original sound. 1 represents 100%
//     * @return A new Sound with delay
//     * @throws UnsupportedAudioFileException
//     */
//    public Sound delay(int delayInMiSec, double decay) throws UnsupportedAudioFileException {
//        double delayInSec = delayInMiSec / 1000;
//        int sampleDelay = (int) ((double) this.af.getSampleRate() * this.af.getChannels() * delayInSec);
//        if (sampleDelay % 2 == 1 && this.af.getChannels() == 2) {
//            sampleDelay += 1;
//        }
//        Sound a = null;
//        try {
//            a = this.SetVolumn(-0.5);//This is to avoid noise
//        } catch (VolumeOutOfRangeException vore) {
//            vore.printStackTrace(); // The VolumeOutOfRangeException will only thrown when try to use
//            //positive numbers in set volume. So I choose to catch this exception here
//            //and do nothing in order not to mess up other progrmas that calls this method.
//        }
//        short[] buffer = a.getShortRepresentation();
//        short[] result = a.getShortRepresentation();
//        for (int i = 0; i < buffer.length - sampleDelay; i++) {
//            result[i + sampleDelay] = (short) (buffer[i] * decay + result[i + sampleDelay]);
//        }
//        ShortBuffer b = ShortBuffer.wrap(buffer);
//        return new Sound(b, this.af);
//    }
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
        short[] a = null;
        short[] c = null;
        if (this.getAf() != b.getAf()) {
            return this;
        } else {
            if ((b.getMaxVolume() + this.getMaxVolume()) >= Short.MAX_VALUE) {
                try {
                    a = this.SetVolumn(-0.5).getShortRepresentation();
                    c = b.SetVolumn(-0.5).getShortRepresentation();
                } catch (VolumeOutOfRangeException vore) {
                    vore.printStackTrace(); // The VolumeOutOfRangeException will only thrown when try to use
                    //positive numbers in set volume. So I choose to catch this exception here
                    //and do nothing in order not to mess up other progrmas that calls this method.
                }
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

class VolumeOutOfRangeException extends Exception {

    public VolumeOutOfRangeException(String message) {
        super(message);
    }

    public VolumeOutOfRangeException() {
    }

}
