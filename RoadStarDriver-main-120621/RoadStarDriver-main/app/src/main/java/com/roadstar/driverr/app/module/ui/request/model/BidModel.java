package com.roadstar.driverr.app.module.ui.request.model;

import com.google.gson.annotations.SerializedName;

public class BidModel {
    @SerializedName("trip_id")
    String trip_id;

    @SerializedName("service_type")
    String service_type;

    @SerializedName("traveller_response")
    String traveller_response;

    @SerializedName("amount")
    String amount;


    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getTraveller_response() {
        return traveller_response;
    }

    public void setTraveller_response(String traveller_response) {
        this.traveller_response = traveller_response;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
