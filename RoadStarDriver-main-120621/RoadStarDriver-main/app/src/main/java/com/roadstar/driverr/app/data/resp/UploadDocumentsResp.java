package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadDocumentsResp {


    @Expose
    @SerializedName("Success")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
