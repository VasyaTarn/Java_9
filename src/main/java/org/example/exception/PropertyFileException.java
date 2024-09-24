package org.example.exception;

import java.io.IOException;

public class PropertyFileException extends IOException {
    public PropertyFileException(String message) {
        super(message);
    }
}
