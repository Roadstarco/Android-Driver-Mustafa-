package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;

public class InternationalTrips {

    @SerializedName("first_name")
    private String first_name ;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public InternationalTrips(String first_name) {
        this.first_name = first_name;
    }
}
