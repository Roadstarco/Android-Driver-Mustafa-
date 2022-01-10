package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;
import com.roadstar.driverr.app.business.BaseItem;

import java.io.Serializable;

public class InternationalRequest implements BaseItem, Serializable {
    @SerializedName("tripto")
    private String tripto;

    @SerializedName("tripfrom")
    private String tripfrom;

    @SerializedName("provider_id")
    private String provider_id;

    public String getTripto() {
        return tripto;
    }

    public void setTripto(String tripto) {
        this.tripto = tripto;
    }

    public String getTripfrom() {
        return tripfrom;
    }

    public void setTripfrom(String tripfrom) {
        this.tripfrom = tripfrom;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @SerializedName("created_at")
    private String created_at;

    @Override
    public int getItemType() {
        return 0;
    }
}
