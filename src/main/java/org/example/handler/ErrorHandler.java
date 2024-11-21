package org.example.handler;

public class ErrorHandler {

    public static void handleException(IllegalArgumentException e) {
        System.out.println("[ERROR] " + e.getMessage() );
    }
}
