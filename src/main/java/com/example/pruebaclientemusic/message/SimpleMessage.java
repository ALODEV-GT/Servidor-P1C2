package com.example.pruebaclientemusic.message;

import java.io.Serializable;

public class SimpleMessage implements Serializable {

    private String message;

    private static final long serialVersionUID = 6529685098267757690L;

    public SimpleMessage() {
    }

    public SimpleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}

