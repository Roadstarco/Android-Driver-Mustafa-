package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;
import com.roadstar.driverr.app.business.BaseItem;

import java.io.Serializable;

public class Requests implements BaseItem, Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("provider_id")
    private String providerId;

    @SerializedName("status")
    private String status;

    @SerializedName("time_left_to_respond")
    private int timeLeftToRespond;


    @SerializedName("request")
    private Request request;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimeLeftToRespond() {
        return timeLeftToRespond;
    }

    public void setTimeLeftToRespond(int timeLeftToRespond) {
        this.timeLeftToRespond = timeLeftToRespond;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public int getItemType() {
        return BaseItem.ITEM_HISTORY;
    }
}
