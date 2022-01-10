package com.roadstar.driverr.app.data.models;

import com.google.gson.annotations.SerializedName;

public class UpdateProfile {

    @SerializedName("first_name")
    private String first_name;


    @SerializedName("last_name")
    private String last_name;


    @SerializedName("email")
    private String email;


    @SerializedName("mobile")
    private String mobile;

    @SerializedName("avatar")
    private String avatar;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
