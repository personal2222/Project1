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
        ShortBuffer raw = SoundModification.SetVolumn(-0.5, rawWav);
        short buffer[] = WaveManager.generateaDuplicate(raw);
        for (int i = 0; i < buffer.length - sampleDelay; i++) {

            buffer[i + sampleDelay] += buffer[i] * decay;
        }
        //byte[] outAudio = new byte[buffer.length * 2];
        //return ByteBuffer.wrap(outAudio).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(buffer);
        return ShortBuffer.wrap(buffer);
    }

}
