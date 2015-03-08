/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author huangjiayu
 */
public class SoundIO {

    public static Sound read(String Filename) throws IOException, UnsupportedAudioFileException {
        File audioFile = new File(Filename);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
        byte[] rawWave;
        int byteLength = (int) audioStream.getFrameLength() * audioStream.getFormat().getFrameSize();
        rawWave = new byte[byteLength];
        audioStream.read(rawWave);
        ByteBuffer a = ByteBuffer.wrap(rawWave);
        ShortBuffer b = a.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        return new Sound(b, audioStream.getFormat());
    }

    public static void write(Sound s, File f) throws IOException {
        AudioInputStream ais = s.getAIS();
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, f);
        ais.close();
    }

    public static void write(Sound s, String filePath) throws IOException {
        File f = new File(filePath);
        SoundIO.write(s, f);
    }

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
