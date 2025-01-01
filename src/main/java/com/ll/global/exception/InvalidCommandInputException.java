package com.ll.global.exception;

import com.ll.global.exception.GlobalException;

public class InvalidCommandInputException extends GlobalException {
    public InvalidCommandInputException(String msg) {
        super(msg);
    }
}
