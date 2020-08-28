package com.waracle.cakemgr.exception;

public class CakeManagerException extends RuntimeException {

    public CakeManagerException() {
        super();
    }

    public CakeManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CakeManagerException(String message) {
        super(message);
    }
}
