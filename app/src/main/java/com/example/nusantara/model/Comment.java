package com.example.nusantara.model;




public class Comment {
    String username;
    String comment;
    long timestamp;

    public Comment(){}

    public Comment(String username, String comment, long timestamp) {
        this.username = username;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
