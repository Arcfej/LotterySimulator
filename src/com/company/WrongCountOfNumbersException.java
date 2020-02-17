package com.company;

/**
 * Thrown if the count of numbers bet on is not 6.
 */
public class WrongCountOfNumbersException extends Exception {

    public WrongCountOfNumbersException(String message) {
        super(message);
    }
}
