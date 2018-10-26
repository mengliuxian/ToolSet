package com.flym.fhzk.data.model;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class User {

    public String username;
    public String userpass;
    public String token;


    public User(String username, String userpass, String token) {
        this.username = username;
        this.userpass = userpass;
        this.token = token;

    }
}
