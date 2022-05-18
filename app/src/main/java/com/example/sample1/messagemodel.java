package com.example.sample1;

import java.util.Date;

public class messagemodel {

    public String message;
    public int messageType;
    public Date messageTime = new Date();
    // Constructor
    public messagemodel(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }
}
