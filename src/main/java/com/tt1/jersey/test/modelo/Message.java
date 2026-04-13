package com.tt1.jersey.test.modelo;


// POJO class for JSON response
public class Message {
    private String greeting;

    private String framework;


    // Constructor
    public Message(String greeting, String framework) {
        this.greeting = greeting;
        this.framework = framework;
    }

    // Getters
    public String getGreeting() {
        return greeting;
    }

    public String getFramework() {
        return framework;
    }

    // Optionally add setters if necessary
}
