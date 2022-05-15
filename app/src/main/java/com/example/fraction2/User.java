package com.example.fraction2;

public class User {
    public String username, email;
    public User(){

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }

}
