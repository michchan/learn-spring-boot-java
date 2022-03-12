package com.michchan.learnspringjava.restfulwebservices.helloWorld;

public class HelloWorldBean {

    private String message = "";

    public HelloWorldBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // This is not required on Java 11 and Spring Boot 2.6.4
    //@Override
    //public String toString() {
    //    return String.format("HelloWorldBean [message=%s]", message);
    //}
}
