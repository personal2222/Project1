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
import java.net.URL;
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
    }

    public Sound_alt(File file) {
        mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        audioFile = file;;
    }

    public void play() throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        File qwe = new File("./src/hw01/ccc.wav");
        URL soundURL = audioFile.toURI().toURL();
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
        byte[] b;
        b = new byte[(int) audioStream.getFrameLength() * 2 + 1];
        System.out.println(audioStream.getFrameLength());
        int a = audioStream.read(b);
        System.out.print("\n");
        ByteArrayInputStream k = new ByteArrayInputStream(b);
        AudioFormat c = audioStream.getFormat();
        AudioInputStream out = new AudioInputStream(k, c, (int) audioStream.getFrameLength() * 2 + 1);
        AudioSystem.write(out, AudioFileFormat.Type.WAVE, qwe);

    }
}
