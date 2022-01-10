package com.roadstar.driverr.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ChangePasswrodModel {

    @SerializedName("password")
    private String password;

    @SerializedName("password_confirmation")
    private String password_confirmation;

    @SerializedName("password_old")
    private String password_old;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getPassword_old() {
        return password_old;
    }

    public void setPassword_old(String password_old) {
        this.password_old = password_old;
    }
}
