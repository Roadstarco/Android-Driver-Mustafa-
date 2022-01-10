package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;

import java.io.Serializable;
import java.util.List;

public class RequestsModel implements BaseItem, Serializable {


    @SerializedName("account_status")
    private String accountStatus;

    @SerializedName("service_status")
    private String serviceStatus;

    @SerializedName("requests")
    private List<Requests> requests;

    @SerializedName("user_trips")
    private List<InternationTripModel> user_trips;

    public List<InternationTripModel> getUser_trips() {
        return user_trips;
    }

    public void setUser_trips(List<InternationTripModel> user_trips) {
        this.user_trips = user_trips;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public List<Requests> getRequests() {
        return requests;
    }

    public void setRequests(List<Requests> requests) {
        this.requests = requests;
    }

    @Override
    public int getItemType() {
        return BaseItem.ITEM_HISTORY;
    }
}
