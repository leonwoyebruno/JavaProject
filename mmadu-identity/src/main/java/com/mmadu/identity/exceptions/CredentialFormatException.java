package com.mmadu.identity.exceptions;

public class CredentialFormatException extends RuntimeException {
    public CredentialFormatException() {
    }

    public CredentialFormatException(String message) {
        super(message);
    }

    public CredentialFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
