package com.roadstar.driverr.app.data.resp;

import java.io.Serializable;

public class Service implements Serializable {

    public String id;
    public String provider_id;
    public String service_type_id;
    public String status;
    public String service_number;
    public String service_model;


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

    public String getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService_number() {
        return service_number;
    }

    public void setService_number(String service_number) {
        this.service_number = service_number;
    }

    public String getService_model() {
        return service_model;
    }

    public void setService_model(String service_model) {
        this.service_model = service_model;
    }
}
