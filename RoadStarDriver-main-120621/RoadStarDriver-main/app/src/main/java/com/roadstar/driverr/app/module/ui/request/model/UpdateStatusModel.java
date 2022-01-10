package com.roadstar.driverr.app.module.ui.request.model;

import com.google.gson.annotations.SerializedName;

public class UpdateStatusModel  {

    @SerializedName("trip_id")
    private Integer trip_id;

    @SerializedName("trip_status")
    private String trip_status;

    @SerializedName("airport")
    private String airport;

    @SerializedName("flight_no")
    private String flight_no;

    public Integer getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }
}
