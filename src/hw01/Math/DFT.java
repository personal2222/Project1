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

import hw01.Sound;

/**
 *
 * @author Zhengri Fan
 *
 *
 *
 */
public class DFT {

    /**
     *
     * Performs the DFT using the FFT approach of the given sound object.
     *
     * @param sound the Sound object to perform the DFT operation
     * @return An array of Complex numbers representing the result of the DFT
     * @throws LengthNotAPowerOfTwoException
     *
     * Note: It won't actually throw the LengthNotAPowerOfTwoException since it
     */
    public static Complex[] SoundDFT(Sound sound) throws LengthNotAPowerOfTwoException {
        short[] rawSound = sound.getShortRepresentation();
        Complex[] waveRepr = new Complex[rawSound.length];
        System.out.println("Read Wave");
        for (int i = 0; i < waveRepr.length; ++i) {
            waveRepr[i] = new Complex(rawSound[i] / 1024, 0);
        }
        waveRepr = DFT.extendArrayToPowOfTwo(waveRepr);
        Complex[] result = DFT.FTransform(waveRepr);
        return result;
    }

    /**
     * This is the original DFT; it directly implements the definition of DFT
     *
     * @param series
     * @return
     */
    public static Complex[] Transform(Complex[] series) {
        int inputLength = series.length;
        Complex[] k_array = new Complex[inputLength];
        for (int k = 0; k < inputLength; ++k) {
            Complex sum = new Complex(0, 0);
            for (int t = 0; t < inputLength; ++t) {
                sum = sum.add(DFT.exponent(t, k, inputLength).exp().mul(series[t]));
            }
            k_array[k] = sum;
        }
        return k_array;
    }

    /**
     *
     * This program implements the Fast Fourier Transform with the sample code
     * mentioned below:
     *
     *
     * @see http://bbs.csdn.net/topics/390785412
     *
     * Assumes that the array is of length of power of 2
     * @param series
     * @return
     */
    public static Complex[] FTransform(Complex[] series) throws LengthNotAPowerOfTwoException {
        int inputLength = series.length;
        if (inputLength == 1) {
            return series;
        } else if (inputLength % 2 != 0) {
            throw new LengthNotAPowerOfTwoException("Array length is not a power of 2");
        }
        Complex[] even = new Complex[inputLength / 2];
        Complex[] odd = new Complex[inputLength / 2];
        for (int i = 0; i < inputLength / 2; ++i) {
            even[i] = series[2 * i];
            odd[i] = series[2 * i + 1];
        }
        Complex[] evenResult = DFT.FTransform(even);
        Complex[] oddResult = DFT.FTransform(odd);

        Complex[] result = new Complex[inputLength];

        for (int i = 0; i < inputLength / 2; ++i) {
            Complex factor = DFT.exponent(1, i, inputLength).exp();
            result[i] = evenResult[i].add(factor.mul(oddResult[i]));
            result[i + inputLength / 2] = evenResult[i].sub(factor.mul(oddResult[i]));
        }
        return result;
    }

//    /**
//     * http://bbs.csdn.net/topics/390785412
//     *
//     * @param x
//     * @return
//     */
//    public static Complex[] fft(Complex[] x) {
//        int N = x.length;
//
//        // base case
//        if (N == 1) {
//            return new Complex[]{x[0]};
//        }
//
//        // radix 2 Cooley-Tukey FFT
//        if (N % 2 != 0) {
//            throw new RuntimeException("N is not a power of 2");
//        }
//
//        // fft of even terms
//        Complex[] even = new Complex[N / 2];
//        for (int k = 0; k < N / 2; k++) {
//            even[k] = x[2 * k];
//        }
//        Complex[] q = DFT.fft(even);
//
//        // fft of odd terms
//        Complex[] odd = even;  // reuse the array
//        for (int k = 0; k < N / 2; k++) {
//            odd[k] = x[2 * k + 1];
//        }
//        Complex[] r = DFT.fft(odd);
//
//        // combine
//        Complex[] y = new Complex[N];
//        for (int k = 0; k < N / 2; k++) {
//            double kth = -2 * k * Math.PI / N;
//            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
//            y[k] = q[k].add(wk.mul(r[k]));
//            y[k + N / 2] = q[k].sub(wk.mul(r[k]));
//        }
//        return y;
//    }
    private static Complex[] extendArrayToPowOfTwo(Complex[] inputArray) {
        int arrayLength = inputArray.length;
        int extraSlot = DFT.determineMissingSlots(arrayLength);
        Complex[] result = new Complex[arrayLength + extraSlot];
        for (int j = 0; j < arrayLength; ++j) {
            result[j] = inputArray[j];
        }
        for (int j = arrayLength; j < result.length; ++j) {
            result[j] = new Complex(0, 0);
        }
        return result;
    }

    private static int determineMissingSlots(int arrayLength) {
        int extraSlot = 0;
        int i = 0;
        if (arrayLength == 1) {
            return 1;
        }
        while (true) {
            if (arrayLength == 1) {
                break;
            } else if (arrayLength % 2 == 1) {
                extraSlot += Math.pow(2, i);
                arrayLength += 1;
            }
            arrayLength = arrayLength / 2;
            i++;
        }
        return extraSlot;
    }

    private static Complex exponent(int t, int k, int n) {
        double imag = (-2 * Math.PI * t * k) / n;
        return new Complex(0, imag);
    }

}

class LengthNotAPowerOfTwoException extends Exception {

    public LengthNotAPowerOfTwoException(String errMsg) {
        super(errMsg);
    }
}
