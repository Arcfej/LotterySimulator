package com.company;

/**
 * Thrown if the bet-number is not in the range of the Lottery System
 */
public class NotValidNumberException extends Exception {
    public NotValidNumberException(String message) {
        super(message);
    }
}
