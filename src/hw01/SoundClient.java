/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Scanner;
import javafx.scene.media.MediaException;

/**
 *
 * @author huangjiayu
 */
public class SoundClient {

    public static void main(String[] args) throws MalformedURLException {

        selectionMenu();
    }

    public static void selectionMenu() throws MalformedURLException {
        System.out.println("Pls select what do you want ?");
        System.out.println("Select 1 to process a file");
        System.out.println("Select 3 to exit");
        Scanner select = new Scanner(System.in);
        switch (select.nextInt()) {
            case 1:
                process();
                break;
            case 3:
                break;
        }
    }

    public static void process() throws MalformedURLException, MediaException {
        while (true) {
            System.out.println("PLS enter the audiofile's address to process. Type 0 to exit");
            Scanner play = new Scanner(System.in);
            String fileaddress = play.nextLine();
            if ("0".equals(fileaddress)) {
                break;
            }
            File s = new File(fileaddress);
            Sound sound = new Sound(s);
            while (true) {
                System.out.println("What do you want to do with this sound file?");
                System.out.println("1: play 2:adjest volumn 3: add an echo 4: Add reverberation 5:shrink the file 6: change a file");
                int select = play.nextInt();
                if (select == 6) {
                    break;
                }
                switch (select) {
                    case 1:
                        sound.play();
                        break;
                    case 2:
                        volumnsetting(sound);
                        break;

                }
            }

        }
    }

    public static void volumnsetting(Sound s) {
        System.out.println("Volumn now is " + s.getVolumn() + "\nWhat volumn do you want to add or minus?");
        Scanner volumn = new Scanner(System.in);
        double addvalue = volumn.nextDouble();
        double vol = addvalue + s.getVolumn();
        if (vol <= 1.2) {
            s.setVolumn(vol);
        } else {
            System.out.println("Vol: " + vol + " is out of range, so the volumn is set to maximum 1.2");
            s.setVolumn(1.2);
        }
        System.out.println("Volumn now is " + s.getVolumn());
    }

}
