package com.roadstar.driverr.app.data.models;

import com.google.gson.annotations.SerializedName;

public class StatusChangeModel {
    @SerializedName("service_status")
    private String service_status;

    public String getService_status() {
        return service_status;
    }

    public void setService_status(String service_status) {
        this.service_status = service_status;
    }
}
