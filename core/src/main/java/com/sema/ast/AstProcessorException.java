package com.sema.ast;

public class AstProcessorException extends RuntimeException {
    public AstProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AstProcessorException(String message) {
        super(message);
    }
}
