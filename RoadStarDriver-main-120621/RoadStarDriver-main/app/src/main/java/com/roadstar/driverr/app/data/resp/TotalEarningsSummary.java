package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;


public class TotalEarningsSummary {

    @SerializedName("rides")
    private String rides;


    @SerializedName("scheduled_rides")
    private String scheduled_rides;


    @SerializedName("cancel_rides")
    private String cancel_rides;


    @SerializedName("revenue")
    private String revenue;


    @SerializedName("internationalRidesRevenue")
    private String internationalRidesRevenue;

    public String getInternationalRidesRevenue() {
        return internationalRidesRevenue;
    }

    public void setInternationalRidesRevenue(String internationalRidesRevenue) {
        this.internationalRidesRevenue = internationalRidesRevenue;
    }

    public String getRides() {
        return rides;
    }

    public void setRides(String rides) {
        this.rides = rides;
    }

    public String getScheduled_rides() {
        return scheduled_rides;
    }

    public void setScheduled_rides(String scheduled_rides) {
        this.scheduled_rides = scheduled_rides;
    }

    public String getCancel_rides() {
        return cancel_rides;
    }

    public void setCancel_rides(String cancel_rides) {
        this.cancel_rides = cancel_rides;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }
}
