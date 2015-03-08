/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Zhengri Fan
 */
public class testSound {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Sound s = new Sound("./testTone.wav");
////        for (short x : s.getShortRepresentation()) {
////            System.out.println(x);
////        }
        s.play();
//        System.out.println("sound closed");
////        byte[] tone = genTone.generatePureTone(400, 0.5, 3, genTone.ToneType.SINE);;
////        ShortBuffer sb = ByteBuffer.wrap(tone).asShortBuffer();
//////        short[] dst = new short[sb.limit()];
//////        sb.get(dst);
//////        for (short x : dst) {
//////            System.out.println(x);
//////        }
////        Sound s1 = new Sound(sb, new AudioFormat(
////                             PCM_SIGNED, 44100, 8, 1, 4, 44100, false));
////        SoundIO.write(s1, "./etst.wav");
//////        for (short x : s1.getShortRepresentation()) {
//////            System.out.println(x);
//////        }
//        Sound s1 = genTone.generatePureToneAsSound(400, 0.5, 3, genTone.ToneType.SINE);
//        s1.play();
//        SoundIO.write(s1, new File("./testTone.wav"));

    }
}
