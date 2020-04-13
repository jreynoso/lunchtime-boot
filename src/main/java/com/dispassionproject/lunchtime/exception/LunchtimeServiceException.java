package com.dispassionproject.lunchtime.exception;

public class LunchtimeServiceException extends RuntimeException {

    public LunchtimeServiceException(String message) {
        super(message);
    }

    public LunchtimeServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
