package com.vlad.exceptions;

/**
 * Created by Fedyunkin_Vladislav on 17.02.2017.
 */
public class SQLFailException extends Exception {
    public SQLFailException() {
    }

    public SQLFailException(String message) {
        super("Failed to proceed SQL Query : " + message);
    }
}
