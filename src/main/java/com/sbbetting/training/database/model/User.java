package com.sbbetting.training.database.model;

public class User {

    private final String userId;

    private final String email;

    private final String password;

    private final UserDetails userDetails;

    public User(String userId,
                String email,
                String password,
                UserDetails userDetails) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userDetails = userDetails;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
