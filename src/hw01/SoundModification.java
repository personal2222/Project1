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
        int sampleDelay = (int) 44.100 * delayInMiSec;
        short buf = 0;
        short buffer[] = WaveManager.generateaDuplicate(rawWav);
        int limit = WaveManager.findlimit(rawWav);
        for (int i = 0; i < rawWav.limit() - sampleDelay; i++) {
            buf = rawWav.get(i + sampleDelay);
            buf += (short) ((float) rawWav.get(i) * decay);
            if (buf >= limit) {
                buf = (short) limit;
            }
            buffer[i + sampleDelay] = buf;
        }
        return ShortBuffer.wrap(buffer);
    }

}
