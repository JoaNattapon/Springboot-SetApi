package org.example.setapi.Repository;

public class InvalidEntityStateException extends RuntimeException {

    public InvalidEntityStateException(String message) {

        super(message);
    }
}
