package com.roadstar.driverr.app.data.resp;

import java.io.Serializable;

public class Device implements Serializable {

    public String id;
    public String provider_id;
    public String udid;
    public String token;
    public String sns_arn;
    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSns_arn() {
        return sns_arn;
    }

    public void setSns_arn(String sns_arn) {
        this.sns_arn = sns_arn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
