/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01;

import java.nio.ShortBuffer;

/**
 *
 * @author huangjiayu
 */
public class SoundModification {

    public static ShortBuffer SetVolumn(double set, ShortBuffer rawWav) {
        double ratio = 1 + set;
        short buf = 0;
        short buffer[] = WaveManager.generateaDuplicate(rawWav);
        int limit = WaveManager.findlimit(rawWav);
        for (int i = 0; i < rawWav.limit(); i++) {
            buf = rawWav.get(i);
            buf *= ratio;
            if (buf >= limit) {
                buf = (short) limit;
            }
            buffer[i] = buf;
        }
        return ShortBuffer.wrap(buffer);
    }

    public static ShortBuffer echo(int delayInMiSec, double decay, ShortBuffer rawWav) {
        int sampleDelay = (int) (44.100 * (float) delayInMiSec);
        short buf = 0;
        ShortBuffer raw = SoundModification.SetVolumn(-0.5, rawWav);//This is to avoid noise
        short buffer[] = WaveManager.generateaDuplicate(raw);
        for (int i = 0; i < buffer.length - sampleDelay; i++) {

            buffer[i + sampleDelay] += buffer[i] * decay;
        }
        //byte[] outAudio = new byte[buffer.length * 2];
        //return ByteBuffer.wrap(outAudio).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buffer);
        return ShortBuffer.wrap(buffer);
    }

//    public static ShortBuffer reverberation(ShortBuffer rawWav) {
//        ShortBuffer raw1 = SoundModification.echo(120, 0.3, rawWav);
//        ShortBuffer raw2 = SoundModification.echo(30, 0.6, rawWav);
//        return SoundModification.addTwoWave(raw1, raw2);
//    }
    public static ShortBuffer reverberation(ShortBuffer rawWav) {
        ShortBuffer raw2 = SoundModification.echo(2, 0.05, rawWav);
        for (int i = 0; i < 10; i++) {
            ShortBuffer raw1 = SoundModification.echo((int) (i * 30), 0.6, rawWav);
            raw1 = SoundModification.SetVolumn(-0.5, raw1);
            raw2 = SoundModification.SetVolumn(-0.5, raw2);
            raw2 = SoundModification.addTwoWave(raw1, raw2);
        }
        for (int i = 0; i < 5; i++) {
            ShortBuffer raw1 = SoundModification.echo((int) (i * 50), i * 0.2, rawWav);
            raw1 = SoundModification.SetVolumn(-0.5, raw1);
            raw2 = SoundModification.SetVolumn(-0.5, raw2);
            raw2 = SoundModification.addTwoWave(raw1, raw2);
        }

        return raw2;
    }

    public static ShortBuffer addTwoWave(ShortBuffer rawWav1, ShortBuffer rawWav2) {
        short buf = 0;
        short buffer1[] = WaveManager.generateaDuplicate(rawWav1);
        short buffer2[] = WaveManager.generateaDuplicate(rawWav2);
        int limit = WaveManager.findlimit(rawWav1);
        for (int i = 0; i < rawWav1.limit(); i++) {
            buffer1[i] += buffer2[i];
        }
        return ShortBuffer.wrap(buffer1);
    }

    public static ShortBuffer downsamplethefilebythree(ShortBuffer raw1) {
        short buffer[] = WaveManager.generateaDuplicate(raw1);
        short buffer2[] = new short[buffer.length / 3];
        for (int i = 0; i < (buffer.length - 2); i++) {
            if (i % 3 == 0) {
                buffer2[i / 3] = buffer[i];
            }

        }
        return ShortBuffer.wrap(buffer2);
    }

}
