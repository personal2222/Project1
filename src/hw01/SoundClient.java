/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Zhengri Fan, Jiayu Huang
 * Date: 2015-3-3
 * Time: 13:24:46
 *
 * Project: csci205
 * Package: hw01
 * File: DFT
 * Description: Project1
 *
 * ****************************************
 */
package hw01;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Jiayu Huang, Zhengri Fan
 */
public class SoundClient {

    private static final Path tempTonePath = Paths.get("./tempFile/pureTone");

    /**
     * Start the SoundClient class
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
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

    /**
     * Initialize the selection menu
     *
     * @throws MalformedURLException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws InterruptedException
     */
    private static void selectionMenu() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
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
                generateToneMenu();
                break;
            case 3:
                break;
        }
    }

    /**
     * Process the audio file read.
     *
     *
     *
     * @see
     * <a href="http://stackoverflow.com/questions/551578/how-to-break-multiple-foreach-loop">
     * http://stackoverflow.com/questions/551578/how-to-break-multiple-foreach-loop</a>
     * Learned how to break the outermost loop
     * @throws MalformedURLException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws InterruptedException
     */
    private static void process() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        OUTERMOST:
        while (true) {
            Scanner in = new Scanner(System.in);
            Sound sound = null;
            while (true) {
                System.out.println("Please enter the audiofile's path to process. Type 0 to exit");
                in = new Scanner(System.in);
                String fileaddress = in.nextLine();
                if ("0".equals(fileaddress)) {
                    break OUTERMOST;
                }
                try {
                    sound = SoundIO.read(fileaddress);
                    break;
                } catch (IOException ioe) {
                    System.out.println("File unavailable to use, please chech the file path and try again");
                    continue;
                } catch (UnsupportedAudioFileException uafe) {
                    System.out.println("Audio file not supported, please change a file and try again");
                    continue;
                }
            }
            modifySouond(sound);
        }
    }

    /**
     * Modify sound menu
     *
     * @param sound a sound object to be modified
     * @throws LineUnavailableException
     * @throws InterruptedException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private static void modifySouond(Sound sound) throws LineUnavailableException, InterruptedException, UnsupportedAudioFileException, IOException {
        Scanner in;
        int select = 0;
        while (true) {
            System.out.println("What do you want to do with this sound file?");
            System.out.println("1: play 2:adjest volumn 3: add an echo 4: Add reverberation 5:shrink the file 6: change a file 7: To save the file");
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                select = in.nextInt();
                break;
            }
            System.out.println("Please give 1 to 6 as input.");
        }
        while (true) {
            in = new Scanner(System.in);
            System.out.println("What do you want to do with this sound file?");
            System.out.println("1: play 2:adjest volumn 3: add an echo 4: Add reverberation 5:shrink the file 6: change a file 7: To save the file");
            if (select == 6) {
                break;
            }
            switch (select) {
                case 1:
                    try {
                        sound.play();
                    } catch (LineUnavailableException lue) {
                        System.err.println("Line Unavailable; program terminates.");
                        throw lue;
                    } catch (InterruptedException ie) {
                        System.err.println("Sound interrupted");
                        throw ie;
                    }
                    break;
                case 2:
                    volumnsetting(sound);
                    break;
                case 3:
                    Sound temp = echosetting(sound);
                    try {
                        temp.play();
                    } catch (LineUnavailableException lue) {
                        System.err.println("Line Unavailable; program terminates.");
                        throw lue;
                    } catch (InterruptedException ie) {
                        System.err.println("Sound interrupted");
                        throw ie;
                    }
                    outprintsetting(temp);
                    break;
                case 4:
                    Sound temp1 = sound.Reverberation();
                    try {
                        temp1.play();
                    } catch (LineUnavailableException lue) {
                        System.err.println("Line Unavailable; program terminates.");
                        throw lue;
                    } catch (InterruptedException ie) {
                        System.err.println("Sound interrupted");
                        throw ie;
                    }
                    outprintsetting(temp1);
                    break;
                case 5:
                    Sound temp2 = sound.downSamplebytwo();
                    try {
                        temp2.play();
                    } catch (LineUnavailableException lue) {
                        System.err.println("Line Unavailable; program terminates.");
                        throw lue;
                    } catch (InterruptedException ie) {
                        System.err.println("Sound interrupted");
                        throw ie;
                    }
                    outprintsetting(temp2);
                    break;
                case 7:
                    outprintsetting(sound);
                    break;
                default:
                    System.out.println("Please give 1 to 6 as input.");
            }
        }
    }

    /**
     * Set the volume
     *
     * @param s
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private static void volumnsetting(Sound s) throws UnsupportedAudioFileException, IOException {
        System.out.println("What volumn do you want to add or minus?");
        Scanner volumn = new Scanner(System.in);
        double addvalue = volumn.nextDouble();
        s.SetthisVolumn(addvalue);
    }

    /**
     * Save the modified audio to a path
     *
     * @param s
     * @throws IOException
     */
    private static void outprintsetting(Sound s) throws IOException {
        while (true) {
            System.out.println("Give me a file path to store the file.");
            try {
                Scanner out = new Scanner(System.in);
                String filepath = out.nextLine();
                System.out.println("Saving...");
                SoundIO.write(s, filepath);
                System.out.print("Saved, the path is " + filepath);
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot write file to the given path, please check the path and try again.");
            }
        }
    }

    /**
     * make a echo of the sound
     *
     * @param s
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private static Sound echosetting(Sound s) throws UnsupportedAudioFileException {
        System.out.println("To do echo effect, pls enter a delay value you want to use");
        Scanner echo = new Scanner(System.in);
        int delay = echo.nextInt();
        System.out.println("pls enter a decay value you want to use");
        double decay = echo.nextDouble();
        return s.echo(delay, decay);
    }

    /**
     * Menu used for generate a pure tone.
     *
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void generateToneMenu() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        System.out.println("Please specify the the frequency, amplitude and the duration of the pure tone you want to generate.");
        double freq, amplitude, duration = 0;
        System.out.println("Please give the frequency of the generated tone in Hz.");
        freq = askToneFrequency();
        amplitude = askToneAmplitude();
        duration = askToneDuration();
        System.out.printf(
                "Now generating a pure tone with frequency: %.3fHz, amplitude: %.3f, and duration %.3fs", freq, amplitude, duration);

        System.out.println(
                "What type of wave do you want to generate?\n1 for sine wave\n2 for square wave\n3 for sawtooth wave.");
        Sound toneSound = warpToneWaveAsSound(genToneAsSpecified(freq, amplitude, duration));
        playOrSaveTone(toneSound, duration);
    }

    /**
     * Ask for the tone frequency
     *
     * @return the tone frequency that the user want to generate
     */
    private static double askToneFrequency() {
        Scanner in = new Scanner(System.in);
        double freq;
        while (!in.hasNextDouble()) {
            in = new Scanner(System.in);
            System.out.println("We can only take double numbers as frequency, please try again.");
        }
        freq = in.nextDouble();
        return freq;
    }

    /**
     * Ask for the tone amplitude
     *
     * @return the tone amplitude that the user want to generate
     */
    private static double askToneAmplitude() {
        double amplitude = 0;
        Scanner in;
        while (true) {
            in = new Scanner(System.in);
            System.out.println("Please give the amplitude of the generated tone. The amplitude can only be from 0 to 1.");
            if (in.hasNextDouble()) {
                amplitude = in.nextDouble();
                if (amplitude < 0 || amplitude > 1) {
                    System.out.println("Amplitude could only be from 0 to 1, please try again.");
                    continue;
                }
                break;
            } else {
                System.out.println("We can only take double numbers as amplitude, please try again.");
            }
        }
        return amplitude;
    }

    /**
     * Ask for the tone duration
     *
     * @return the tone duration that the user want to generate
     */
    private static double askToneDuration() {
        double duration = 0;
        Scanner in;
        while (true) {
            in = new Scanner(System.in);
            System.out.println("Please give the duration of the generated tone in seconds.");
            if (in.hasNextDouble()) {
                duration = in.nextDouble();
                break;
            } else {
                System.out.println("We can only take double numbers as duration, please try again.");
            }
        }
        return duration;
    }

    /**
     * Generate the actual tone with the given frequency, amplitude and
     * duration.
     *
     * @param freq Tone frequency
     * @param amplitude Tone amplitude
     * @param duration Tone duration
     * @return an byte array of the generated new tone.
     */
    private static byte[] genToneAsSpecified(double freq, double amplitude, double duration) {
        byte[] toneWave = new byte[1];
        Scanner in;
        while (true) {
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                switch (in.nextInt()) {
                    case 1:
                        toneWave = genTone.generatePureTone(freq, amplitude, duration, genTone.ToneType.SINE);
                        break;
                    case 2:
                        toneWave = genTone.generatePureTone(freq, amplitude, duration, genTone.ToneType.SQUARE);
                        break;
                    case 3:
                        toneWave = genTone.generatePureTone(freq, amplitude, duration, genTone.ToneType.SAWTOOTH);
                        break;
                    default:
                        System.out.println("We can only take intergers from 1 to 3 as input, please try again.");
                        continue;
                }
                break;
            } else {
                System.out.println("We can only take intergers from 1 to 3 as input, please try again.");
            }
        }
        return toneWave;
    }

    /**
     * warp the byte wave to a sound object.
     *
     * @param toneWave
     * @return
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    private static Sound warpToneWaveAsSound(byte[] toneWave) throws IOException, UnsupportedAudioFileException {
        Sound pureTone = genTone.translateToSound(toneWave);
        File toneFile = new File(tempTonePath.toUri());
        toneFile.getParentFile().mkdirs();
        SoundIO.write(pureTone, toneFile);
        pureTone = null;
        Sound toneSound = new Sound(tempTonePath.toString());
        return toneSound;
    }

    /**
     * Play or save the generated tone
     *
     * @see For creating a path that is not existed in the file system.
     * <a href="http://stackoverflow.com/questions/2833853/create-whole-path-automatically-when-writing-to-a-new-file">
     * http://stackoverflow.com/questions/2833853/create-whole-path-automatically-when-writing-to-a-new-file</a>
     *
     * @param toneSound
     * @param duration
     * @throws IOException
     * @throws InterruptedException
     * @throws LineUnavailableException
     */
    private static void playOrSaveTone(Sound toneSound, double duration) throws IOException, InterruptedException, LineUnavailableException {
        Scanner in;
        System.out.println("Tone generated successfully.");
        while (true) {
            System.out.println("What do you want to do next?");
            System.out.println("1 for play the generated tone\n2 for save it as a file and exit\n0 for exit");
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                int usrChoice = in.nextInt();
                if (usrChoice == 1) {
                    toneSound.play(duration);
                } else if (usrChoice == 2) {
                    writePureToneOut(toneSound);
                    break;
                } else if (usrChoice == 0) {
                    break;
                } else {
                    System.out.println("Please enter 1, 2 or 0");
                    continue;
                }
            } else {
                System.out.println("Please enter 1, 2 or 0");
            }
        }
    }

    /**
     * Write the tone generated out to a file
     *
     * @param toneSound the Sound object representing the tone.
     * @throws IOException
     */
    private static void writePureToneOut(Sound toneSound) throws IOException {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter the path that you want to store the file");
            String path = in.next();
            try {
                File destinationFile = new File(path);
                destinationFile.getParentFile().mkdirs();
                SoundIO.write(toneSound, destinationFile);
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot write file to the given path, please check the path and try again.");
            }
        }
    }

}
