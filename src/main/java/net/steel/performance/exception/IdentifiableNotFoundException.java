package net.steel.performance.exception;

public class IdentifiableNotFoundException extends Exception {

    public IdentifiableNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
