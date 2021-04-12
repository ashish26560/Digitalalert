package com.example.digitalalertsystem;

public class User {
    String fullname, phoneNo, email;

    public User (){

    }

    public User(String fullname, String phoneNo, String email) {
        this.fullname = fullname;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
