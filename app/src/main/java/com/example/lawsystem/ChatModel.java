package com.example.lawsystem;

public class ChatModel {
    String message;
    String fromName;
    String toName;
    boolean isUser;
    boolean isRead;
    String key;
    boolean isPayment;
    boolean isFile;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public ChatModel(String message,String fromName, String toName, boolean isUser, boolean isRead, String key, boolean isPayment, boolean isFile) {
        this.message = message;

        this.fromName = fromName;
        this.toName = toName;
        this.isUser = isUser;
        this.isRead = isRead;
        this.key = key;
        this.isPayment = isPayment;
        this.isFile = isFile;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ChatModel() {
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }
}
