package com.sema.parser.web;

public class AstErrorResponse {
    private final String status;
    private final String message;

    public AstErrorResponse(String message) {
        this.status = "error";
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
