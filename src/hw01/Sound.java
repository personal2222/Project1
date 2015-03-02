/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javafx.scene.media.AudioClip;
import javax.sound.sampled.Mixer;
import javax.swing.JFileChooser;

/**
 *
 * @author zf002
 */
public class Sound {

    private File file;
    private AudioClip clip;
    private Mixer mixer;

    /**
     * https://www.youtube.com/watch?v=nUKya2DvYSo
     *
     * @param fileName
     */
    public Sound(File file) throws MalformedURLException {
        this.file = file;
        //Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        //this.mixer = AudioSystem.getMixer(mixerInfo[0]);
        //DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        URL resource = file.toURI().toURL();
        try {
            this.clip = new AudioClip(resource.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Sound(String fileName) throws MalformedURLException {
        this.file = new File(fileName);
        //Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        //this.mixer = AudioSystem.getMixer(mixerInfo[0]);
        //DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        URL resource = file.toURI().toURL();
        try {
            this.clip = new AudioClip(resource.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {
        clip.play();
    }

    public void setVolumn(double ratio) {
        clip.setVolume(ratio);
    }

    public double getVolumn() {
        double i = clip.getVolume();
        return i;
    }

    public static void main(String[] args) throws MalformedURLException {

        JFileChooser fc = new JFileChooser(".");
        fc.showOpenDialog(null);
        File file1 = fc.getSelectedFile();
        Sound sound = new Sound(file1);
        System.out.println("Now the volumn is " + sound.getVolumn());
        Scanner a = new Scanner(System.in);
        double volumn = a.nextDouble();
        sound.setVolumn(volumn);
        System.out.println("Now the volumn is " + sound.getVolumn());

        //sound.play();
    }

}
