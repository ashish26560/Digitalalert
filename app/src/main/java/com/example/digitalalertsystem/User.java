package com.example.digitalalertsystem;

public class User {
    String fullname, userPhoneNo, email,  emergencyPhoneNo1, emergencyPhoneNo2;
    //
    public User (){

    }
    //
    public User(String fullname, String userPhoneNo,String email, String emergencyPhoneNo1, String emergencyPhoneNo2) {
        this.fullname = fullname;
        this.userPhoneNo = userPhoneNo;
        this.email = email;
        this.emergencyPhoneNo1 = emergencyPhoneNo1;
        this.emergencyPhoneNo2 = emergencyPhoneNo2;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyPhoneNo1() {
        return emergencyPhoneNo1;
    }

    public void setEmergencyPhoneNo1(String emergencyPhoneNo1) {
        this.emergencyPhoneNo1 = emergencyPhoneNo1;
    }

    public String getEmergencyPhoneNo2() {
        return emergencyPhoneNo2;
    }

    public void setEmergencyPhoneNo2(String emergencyPhoneNo2) {
        this.emergencyPhoneNo2 = emergencyPhoneNo2;
    }
}
