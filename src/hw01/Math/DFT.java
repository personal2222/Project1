/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2015
 *
 * Name: Zhengri Fan, Jiayu Huang
 * Date: 2015-2-28
 * Time: 23:24:19
 *
 * Project: csci205
 * Package: hw01.Math
 * File: DFT
 * Description: Project1
 *
 * ****************************************
 */
package hw01.Math;

import hw01.Sound;

/**
 * @author Jiayu Huang, Zhengri Fan
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
     * will add 0s to the array so that it actually have length of power of 2
     */
    public static Complex[] SoundDFT(Sound sound) throws LengthNotAPowerOfTwoException {
        short[] rawSound = sound.getShortRepresentation();
        Complex[] waveRepr = new Complex[rawSound.length];
        System.out.println("Reading Wave");
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
     * @param series An array of complex numbers to perform the DFT
     * @return the result as an array of complex numbers
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
     * This program implements the Fast Fourier Transform for performing DFT
     *
     * Adapted from the code shown in the link below
     *
     * @see
     * <a href="http://bbs.csdn.net/topics/390785412">
     * http://bbs.csdn.net/topics/390785412</a>
     *
     * @param series An array of complex numbers to perform the DFT
     * @return the result as an array of complex numbers
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

    /**
     * Add 0s to the imput array so that it will actually have a length of power
     * of 2
     *
     * @param inputArray: an array of complex numbers to be converted into a new
     * array with extra 0s such that it would have a length of power of 2
     * @return a complex array of length of 2
     */
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

    /**
     * Find how many 0s should be added such that the final array length would
     * be a power of 2
     *
     * @param arrayLength
     * @return
     */
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

    /**
     * Return -2 * PI * t * k * i /n, where i is Sqrt(-1)
     *
     * @param t
     * @param k
     * @param n
     * @return
     */
    private static Complex exponent(int t, int k, int n) {
        double imag = (-2 * Math.PI * t * k) / n;
        return new Complex(0, imag);
    }

    /**
     * Return the maximum n peaks indicated by numPeaksInterested as a double
     * array
     *
     * @see
     * <a href="http://www.dosits.org/science/soundmeasurement/soundshear/">
     * http://www.dosits.org/science/soundmeasurement/soundshear/</a>
     * @param sound the sound object for performing a DFT
     * @param numPeaksInterested top n peaks interested.
     * @return a double array of top n frequencies
     * @throws LengthNotAPowerOfTwoException
     */
    public static double[] DFTResult(Sound sound, int numPeaksInterested) throws LengthNotAPowerOfTwoException {
        int maxHumanFreq = 20000;
        int minHumanFreq = 20;
        Complex[] afterTransform = SoundDFT(sound);
        int startingIndex = (int) (minHumanFreq * afterTransform.length / sound.getAf().getSampleRate());
        int endingIndex = (int) (maxHumanFreq * afterTransform.length / sound.getAf().getSampleRate());
        int[] maxIndexArray = findMaxNIndex(startingIndex, endingIndex, numPeaksInterested, afterTransform);
        double[] maxFreqArray = new double[maxIndexArray.length];
        for (int i = 0; i < maxIndexArray.length; ++i) {
            maxFreqArray[i] = maxIndexArray[i] * sound.getAf().getSampleRate() / afterTransform.length;
        }
        return maxFreqArray;
    }

    /**
     * Find the index of the max n magnitudes of an array of complex numbers and
     * return the max index as an int array.
     *
     * @param startingIndex the starting index for finding the max
     * @param endingIndex the endinging index for finding the max
     * @param numPeaksInterested how many max values we want to find
     * @param afterTransform a Complex array for finding maximum
     * @return
     */
    private static int[] findMaxNIndex(int startingIndex, int endingIndex, int numPeaksInterested, Complex[] afterTransform) {
        double[] maxMagnitudeArray = new double[numPeaksInterested];
        int[] maxIndexArray = new int[numPeaksInterested];
        for (int i = 0; i < maxMagnitudeArray.length; ++i) {
            maxMagnitudeArray[i] = Double.MIN_VALUE;
        }
        for (int i = startingIndex; i < endingIndex; ++i) {
            for (int j = 0; j < numPeaksInterested; ++j) {
                double resultMagnitude = afterTransform[i].magnitude();
                if (resultMagnitude > maxMagnitudeArray[j]) {
                    for (int k = numPeaksInterested - 2; k >= j; --k) {
                        maxMagnitudeArray[k + 1] = maxMagnitudeArray[k];
                        maxIndexArray[k + 1] = maxIndexArray[k];
                    }
                    maxMagnitudeArray[j] = resultMagnitude;
                    maxIndexArray[j] = i;
                    break;
                }
            }
        }
        return maxIndexArray;
    }

}
