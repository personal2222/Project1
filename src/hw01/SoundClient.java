/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author huangjiayu
 */
public class SoundClient {

    public static void main(String[] args) throws MalformedURLException {
        JFileChooser fc = new JFileChooser(".");
        fc.showOpenDialog(null);
        File file1 = fc.getSelectedFile();
        Sound sound = new Sound(file1);
        System.out.println("Now the volumn is " + sound.getVolumn());
        sound.play();
        while (true) {
            Scanner a = new Scanner(System.in);
            double volumn = a.nextDouble();
            sound.setVolumn(volumn);
            System.out.println("Now the volumn is " + sound.getVolumn());
            sound.play();
        }
    }

    public static void selectionMenu() {
        System.out.println("Pls select what do you want ?");
        System.out.println("Select 1 to play a file");
        Scanner select = new Scanner(System.in);
        switch (select.nextInt()) {
            case 1:
                play();
                break;
        }
    }

    public static void play() {
        while (true) {

        }
    }
