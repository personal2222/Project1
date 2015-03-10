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
 * File: SoundClient
 * Description: Project1
 *
 * ****************************************
 */
package hw01;

import hw01.Math.LengthNotAPowerOfTwoException;
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
    public static void main(String[] args) throws IOException, LengthNotAPowerOfTwoException {
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
    private static void selectionMenu() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException, LengthNotAPowerOfTwoException {
        System.out.println("Please select what do you want to do");
        OUTERMOST:
        while (true) {
            System.out.println("Select 1 to process a file");
            System.out.println("Select 2 to generate a tone");
            System.out.println("Select 3 to exit");
            Scanner select = new Scanner(System.in);
            if (!select.hasNextInt()) {
                System.out.println("We can only handle options from 1 to 3, please try again");
                continue;
            }
            switch (select.nextInt()) {
                case 1:
                    process();
                    break OUTERMOST;
                case 2:
                    generateToneMenu();
                    break OUTERMOST;
                case 3:
                    System.out.println("Goodbye!");
                    break OUTERMOST;
                default:
                    System.out.println("We can only handle options from 1 to 3, please try again");
            }
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
    private static void process() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException, LengthNotAPowerOfTwoException {
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
            boolean EXIT = modifySouond(sound);
            if (EXIT) {
                System.out.println("Goodbye!");
                break;
            }
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
    private static boolean modifySouond(Sound sound) throws LineUnavailableException, InterruptedException, UnsupportedAudioFileException, IOException, LengthNotAPowerOfTwoException {
        Scanner in;
        int select = 0;
        while (true) {
            System.out.println("What do you want to do with this sound file?");
            System.out.println("1: play 2:adjest volumn 3: add an echo 4: Add reverberation 5:shrink the file\n6: change a file 7: To save the file, 8: To exit, 0:Perform a DFT of this sound");
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                select = in.nextInt();
            } else {
                System.out.println("Please give 1 to 7 as input.");
                continue;
            }
            if (select == 6) {
                return false;
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
                case 8:
                    return true;
                case 0:
                    performDFT(sound);
                    break;
                default:
                    System.out.println("Please give 1 to 7 as input.");
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
        while (true) {
            System.out.println("What volumn do you want to add or minus?");
            Scanner volumn = new Scanner(System.in);
            if (!volumn.hasNextDouble()) {
                System.out.println("Plaese input a number as input.");
                continue;
            }
            double addvalue = volumn.nextDouble();
            s.SetthisVolumn(addvalue);
            break;
        }
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
        int delay = 0;
        double decay = 0;
        while (true) {
            System.out.println("To do echo effect, please enter a delay value you want to use in ms");
            Scanner echo = new Scanner(System.in);
            if (!echo.hasNextInt()) {
                System.out.println("Please input an integer for delay value");
                continue;
            }
            delay = echo.nextInt();
            break;
        }
        while (true) {
            System.out.println("Please enter a decay value you want to use");
            Scanner echo = new Scanner(System.in);
            if (!echo.hasNextDouble()) {
                System.out.println("Please input an number for decay value");
                continue;
            }
            decay = echo.nextDouble();
            break;
        }
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
    private static void generateToneMenu() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException, LengthNotAPowerOfTwoException {
        while (true) {
            System.out.println("Please specify the the frequency, amplitude and the duration of the pure tone you want to generate.");
            double freq, amplitude, duration = 0;
            System.out.println("Please give the frequency of the generated tone in Hz.");
            freq = askToneFrequency();
            amplitude = askToneAmplitude();
            duration = askToneDuration();
            System.out.printf(
                    "Now generating a pure tone with frequency: %.3fHz, amplitude: %.3f, and duration %.3fs\n", freq, amplitude, duration);

            System.out.println(
                    "What type of wave do you want to generate?\n1 for sine wave\n2 for square wave\n3 for sawtooth wave.");
            Sound toneSound = warpToneWaveAsSound(genToneAsSpecified(freq, amplitude, duration));
            boolean EXIT = modifyTone(toneSound, duration);
            if (EXIT) {
                System.out.println("Goodbye!");
                break;
            }
        }
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
    private static short[] genToneAsSpecified(double freq, double amplitude, double duration) {
        short[] toneWave = new short[1];
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
    private static Sound warpToneWaveAsSound(short[] toneWave) throws IOException, UnsupportedAudioFileException {
        Sound pureTone = genTone.translateToSound(toneWave);
        File toneFile = new File(tempTonePath.toUri());
        toneFile.getParentFile().mkdirs();
        SoundIO.write(pureTone, toneFile);
        pureTone = null;
        Sound toneSound = new Sound(tempTonePath.toString());
        return toneSound;
    }

    /**
     * Modify tone menu
     *
     * @param sound a sound object to be modified
     * @throws LineUnavailableException
     * @throws InterruptedException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private static boolean modifyTone(Sound tone, double duration) throws LineUnavailableException, InterruptedException, UnsupportedAudioFileException, IOException, LengthNotAPowerOfTwoException {
        int select = 0;
        while (true) {
            System.out.println("What do you want to do with this sound file?");
            System.out.println("1: play 2:adjest volumn 3: add an echo 4: Add reverberation\n5:shrink the file 6: To regenerate a tone 7: To save the file 8: To exit 0: To perform DFT");
            Scanner in = new Scanner(System.in);
            if (in.hasNextInt()) {
                select = in.nextInt();
            } else {
                System.out.println("Please give 1 to 7 as input.");
                continue;
            }
            if (select == 6) {
                return false;
            }
            switch (select) {
                case 1:
                    try {
                        tone.play(duration);
                        break;
                    } catch (LineUnavailableException lue) {
                        System.err.println("Line Unavailable; program terminates.");
                        throw lue;
                    } catch (InterruptedException ie) {
                        System.err.println("Sound interrupted");
                        throw ie;
                    }
                case 2:
                    volumnsetting(tone);
                    break;
                case 3:
                    Sound temp = echosetting(tone);
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
                    Sound temp1 = tone.Reverberation();
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
                    Sound temp2 = tone.downSamplebytwo();
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
                    outprintsetting(tone);
                    break;
                case 8:
                    return true;
                case 0:
                    performDFT(tone);
                    break;
                default:
                    System.out.println("Please give 1 to 7 as input.");
            }
        }
    }

    /**
     * Perform a DFT for a sound object
     *
     * @param sound
     * @throws LengthNotAPowerOfTwoException
     */
    private static void performDFT(Sound sound) throws LengthNotAPowerOfTwoException {
        System.out.println("Now performing DFT to the given sound");
        System.out.println("We will print out the frequency after the transformation.\nNow starting the transformation.");
        double resultFreq = hw01.Math.DFT.DFTResult(sound);
        System.out.println(String.format("Transform finished. The result is: %.2fhz", resultFreq));
    }
}
