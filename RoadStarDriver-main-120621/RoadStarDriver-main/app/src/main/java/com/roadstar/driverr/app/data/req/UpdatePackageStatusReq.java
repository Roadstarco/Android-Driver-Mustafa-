package com.roadstar.driverr.app.data.req;

import com.google.gson.annotations.SerializedName;

public class UpdatePackageStatusReq {

    @SerializedName("_method")
    private String _method;

    @SerializedName("status")
    private String status;


    public String get_method() {
        return _method;
    }

    public void set_method(String _method) {
        this._method = _method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
