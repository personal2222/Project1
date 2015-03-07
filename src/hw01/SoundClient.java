/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author huangjiayu
 */
public class SoundClient {

    public static void main(String[] args) throws MalformedURLException {

        try {
            selectionMenu();
        } catch (IOException ex) {
            Logger.getLogger(SoundClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(SoundClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SoundClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void selectionMenu() throws MalformedURLException, IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
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

    public static void process() throws MalformedURLException, IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        while (true) {
            System.out.println("PLS enter the audiofile's address to process. Type 0 to exit");
            Scanner play = new Scanner(System.in);
            String fileaddress = play.nextLine();
            if ("0".equals(fileaddress)) {
                break;
            }
            Sound sound = SoundIO.read(fileaddress);
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

    public static void volumnsetting(Sound s) throws UnsupportedAudioFileException, IOException {
        System.out.println("What volumn do you want to add or minus?");
        Scanner volumn = new Scanner(System.in);
        double addvalue = volumn.nextDouble();
        s.SetthisVolumn(addvalue);
    }

}
