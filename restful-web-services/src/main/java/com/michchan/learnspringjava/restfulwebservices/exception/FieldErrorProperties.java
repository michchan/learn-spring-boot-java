package com.michchan.learnspringjava.restfulwebservices.exception;

public class FieldErrorProperties {
    private String code;
    private String message;

    public FieldErrorProperties(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
