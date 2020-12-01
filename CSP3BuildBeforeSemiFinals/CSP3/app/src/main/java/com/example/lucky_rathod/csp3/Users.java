package com.example.lucky_rathod.csp3;

/**
 * Created by Lucky_Rathod on 12-01-2018.
 */

public class Users {
    String email_id;

    public Users(String email_id) {
        this.email_id = email_id;
    }
    public Users(){
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}
