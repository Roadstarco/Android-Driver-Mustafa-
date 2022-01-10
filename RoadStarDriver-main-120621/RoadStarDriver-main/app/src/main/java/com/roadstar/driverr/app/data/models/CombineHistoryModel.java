package com.roadstar.driverr.app.data.models;

import com.google.gson.annotations.SerializedName;
import com.roadstar.driverr.app.data.resp.Request;

import java.util.ArrayList;

public class CombineHistoryModel {

    @SerializedName("localJobs")
    private ArrayList<Request> localJobs;

    @SerializedName("internationalJobs")
    private ArrayList<Request> internationalJobs;

    public ArrayList<Request> getLocalJobs() {
        return localJobs;
    }

    public void setLocalJobs(ArrayList<Request> localJobs) {
        this.localJobs = localJobs;
    }

    public ArrayList<Request> getInternationalJobs() {
        return internationalJobs;
    }

    public void setInternationalJobs(ArrayList<Request> internationalJobs) {
        this.internationalJobs = internationalJobs;
    }

}
