package com.example.registration;

import java.util.Date;

public class Message {
    public String textMessage;
    public String userName;
    private long messageTime;
    public Message(){}

    public Message(String textMessage, String userName) {
        this.textMessage = textMessage;
        this.userName = userName;
        this.messageTime = new Date().getTime();
    }

    public String getTextMessage() {
        return textMessage;
    }
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
