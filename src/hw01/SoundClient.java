/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.FileNotFoundException;
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

    public static void main(String[] args) {

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
        System.out.println("Select 2 to generate a tone");
        System.out.println("Select 3 to exit");
        Scanner select = new Scanner(System.in);
        switch (select.nextInt()) {
            case 1:
                process();
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public static void generateformatmenu() {
        System.out.println("What do you want ");
    }

    public static void process() throws MalformedURLException, FileNotFoundException, IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
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
                System.out.println("1: play 2:adjest volumn 3: add an echo 4: Add reverberation 5:shrink the file 6: change a file 7: To save the file");
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
                    case 3:
                        Sound temp = echosetting(sound);
                        temp.play();
                        outprintsetting(temp);
                    case 4:
                        Sound temp1 = sound.Reverberation();
                        temp1.play();
                        outprintsetting(temp1);
                    case 5:
                        Sound temp2 = sound.downSamplebytwo();
                        temp2.play();
                        outprintsetting(temp2);
                    case 7:
                        outprintsetting(sound);

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

    public static void outprintsetting(Sound s) throws IOException, FileNotFoundException {
        System.out.println("Give me a file path to store the file.");
        Scanner out = new Scanner(System.in);
        String filepath = out.nextLine();
        System.out.println("Saving...");
        SoundIO.write(s, filepath);
        System.out.print("Saved, the path is " + filepath);
    }

    public static Sound echosetting(Sound s) throws UnsupportedAudioFileException, IOException {
        System.out.println("To do echo effect, pls enter a delay value you want to use");
        Scanner echo = new Scanner(System.in);
        int delay = echo.nextInt();
        System.out.println("pls enter a decay value you want to use");
        double decay = echo.nextDouble();
        return s.echo(delay, decay);

    }

}
