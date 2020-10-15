package com.example.cheese.model;

public class Notification {
    private String postId;
    private String userId;
    private String userName;

    public Notification() {
    }

    public Notification(String postId, String userId, String userName) {
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
