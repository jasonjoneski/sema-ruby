package com.sema.parser.web;

public class ParseResponse {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private final String status;
    private String message;
    private String link;

    public ParseResponse(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
