/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Zhengri Fan Jiayu Huang
 * Date: 2015-2-28
 * Time: 23:24:19
 *
 * Project: csci205
 * Package: hw01.Math
 * File: DFT
 * Description:
 *
 * ****************************************
 */
package hw01.Math;

import hw01.WaveManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.Arrays;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Zhengri Fan
 *
 *
 *
 */
public class DFT {

    public static void main(String args[]) throws IOException, UnsupportedAudioFileException {
        ShortBuffer rawWave = hw01.WaveManager.readRawWav(new File("./src/hw01/400.wav"));
        short[] shortWave = WaveManager.generateaDuplicate(rawWave);
        Complex[] waveRepr = new Complex[shortWave.length];

        for (int i = 0; i < waveRepr.length; ++i) {
            waveRepr[i] = new Complex(i / 44100, shortWave[i]);
        }
        double log_2 = Math.log(shortWave.length) / Math.log(2);
        int subArray = (int) Math.pow(2, (int) log_2);
        Complex[] waveRepr2 = new Complex[subArray];
        waveRepr2 = Arrays.copyOfRange(waveRepr, 0, subArray);
        System.out.println("Start Transform");
        Complex[] result = DFT.fft(waveRepr2);
//        for (Complex x : result) {
//            System.out.println(x.magnitude());
//        }
        System.out.println("End Transform");
        FileWriter fw = new FileWriter("./test.csv");
        for (Complex x : result){
            fw.write(x.getReal() + "\n");
        }
        

    }

    public static Complex[] Transform(Complex[] series) {
        int inputLength = series.length;
        Complex[] k_array = new Complex[inputLength];
        for (int k = 0; k < inputLength; ++k) {
            Complex sum = new Complex(0, 0);
            for (int t = 0; t < inputLength; ++t) {
                sum = sum.add(exponent(t, k, inputLength).exp().mul(series[t]));
            }
            k_array[k] = sum;
        }
        return k_array;
    }

    /**
     * http://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
     *
     * @param series
     * @return
     */
    public static Complex[] FTransform(Complex[] series) {
//        double EPISILON = 1E-10;
        int inputLength = series.length;
//        double log_2 = Math.log(inputLength) / Math.log(2);
//        if (log_2 - (int) log_2 > EPISILON) {
//            System.out.print(log_2 - (int) log_2);
//            return null;
//        }
        Complex[] k_array = new Complex[inputLength];
        for (int k = 0; k < inputLength; ++k) {
            k_array[k] = DFT.fastTransform(series, k);
        }
        return k_array;
    }

    public static Complex fastTransform(Complex[] series, int k) {
        int arrayLength = series.length;
        if (arrayLength == 1) {
            return series[0];
        } else if (arrayLength == 2) {
            Complex result = series[0].mul(DFT.exponent(0, k, arrayLength / 2));
            Complex right = series[1].mul(DFT.exponent(0, k, arrayLength / 2));
            right.mul(DFT.exponent(1, k, arrayLength));
            result.add(right);
            return result;
        } else {
            Complex[] odd = new Complex[arrayLength / 2];
            Complex[] even;
            if (arrayLength % 2 == 0) {
                even = new Complex[arrayLength / 2];
            } else {
                even = new Complex[arrayLength / 2 + 1];
            }
            for (int i = 0; i < arrayLength; ++i) {
                if (i % 2 == 1) {
                    odd[i / 2] = series[i];
                } else {
                    even[i / 2] = series[i];
                }
            }
            Complex result = DFT.fastTransform(even, k);
            Complex right = DFT.fastTransform(odd, k);
            right.mul(DFT.exponent(1, k, arrayLength));
            result.add(right);
            return result;
        }

    }

    /**
     * http://bbs.csdn.net/topics/390785412
     *
     * @param x
     * @return
     */
    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) {
            return new Complex[]{x[0]};
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            throw new RuntimeException("N is not a power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd = even;  // reuse the array
        for (int k = 0; k < N / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].add(wk.mul(r[k]));
            y[k + N / 2] = q[k].sub(wk.mul(r[k]));
        }
        return y;
    }

    private static Complex exponent(int t, int k, int n) {
        double imag = (-2 * Math.PI * t * k) / n;
        return new Complex(0, imag);
    }
}
