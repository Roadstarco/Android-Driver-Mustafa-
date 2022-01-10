package com.roadstar.driverr.app.module.ui.your_package.model;

import com.google.gson.annotations.SerializedName;

public class AcceptUserBid {

    @SerializedName("bid_id")
    private Integer bid_id;

    public Integer getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }

    @SerializedName("trip_id")
    private Integer trip_id;

    public String getCounter_amount() {
        return counter_amount;
    }

    public void setCounter_amount(String counter_amount) {
        this.counter_amount = counter_amount;
    }

    @SerializedName("counter_amount")
    private String counter_amount;

    @SerializedName("status")
    private String status;
    @SerializedName("traveller_response")
    private String traveller_response;

    public Integer getBid_id() {
        return bid_id;
    }

    public void setBid_id(Integer bid_id) {
        this.bid_id = bid_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTraveller_response() {
        return traveller_response;
    }

    public void setTraveller_response(String traveller_response) {
        this.traveller_response = traveller_response;
    }
}
