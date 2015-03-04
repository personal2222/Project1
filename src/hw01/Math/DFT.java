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

/**
 *
 * @author Zhengri Fan
 */
public class DFT {

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

    private static Complex exponent(int t, int k, int n) {
        double imag = (-2 * Math.PI * t * k) / n;
        return new Complex(0, imag);
    }
}
