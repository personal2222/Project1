/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw01.Math;

/**
 * Throwed when the length of the array used for DFT is not a power of 2
 *
 * @author Zhengri Fan
 */
public class LengthNotAPowerOfTwoException extends Exception {

    public LengthNotAPowerOfTwoException(String errMsg) {
        super(errMsg);
    }
}
