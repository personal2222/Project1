/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
 */
public class Sound_alt {

    private Mixer mixer;
    private Clip clip;
    private Mixer.Info[] mixInfos;
    private File audioFile;

    public Sound_alt(File file) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        audioFile = file;;

        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        URL soundURL = audioFile.toURI().toURL();
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
        clip.open(audioStream);

        clip.start();

    }
}
