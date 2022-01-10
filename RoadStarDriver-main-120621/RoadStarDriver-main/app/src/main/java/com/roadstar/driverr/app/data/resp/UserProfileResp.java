package com.roadstar.driverr.app.data.resp;

import org.json.JSONObject;

import java.io.Serializable;

public class UserProfileResp extends JSONObject implements Serializable {

    public String id;
    public String first_name;
    public String last_name;
    public String email;
    public String gender;
    public String mobile;
    public String avatar;
    public String rating;
    public String wallet;
    public String commission_payable;
    public String status;
    public String fleet;
    public String latitude;
    public String longitude;
    public String otp;
    public String created_at;
    public String updated_at;
    public String login_by;
    public String social_unique_id;
    public String bank_name;
    public String bank_account_number;
    public String bank_recipient;
    public String bank_ifsc;
    public String access_token;
    public String refresh_token;
    public String currency;
    public String sos;
    public Service service;
    public Device device;
    public String error;
    private String message;
    private boolean documents_uploaded;

    public boolean isDocuments_uploaded() {
        return documents_uploaded;
    }

    public void setDocuments_uploaded(boolean documents_uploaded) {
        this.documents_uploaded = documents_uploaded;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getCommission_payable() {
        return commission_payable;
    }

    public void setCommission_payable(String commission_payable) {
        this.commission_payable = commission_payable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLogin_by() {
        return login_by;
    }

    public void setLogin_by(String login_by) {
        this.login_by = login_by;
    }

    public String getSocial_unique_id() {
        return social_unique_id;
    }

    public void setSocial_unique_id(String social_unique_id) {
        this.social_unique_id = social_unique_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getBank_recipient() {
        return bank_recipient;
    }

    public void setBank_recipient(String bank_recipient) {
        this.bank_recipient = bank_recipient;
    }

    public String getBank_ifsc() {
        return bank_ifsc;
    }

    public void setBank_ifsc(String bank_ifsc) {
        this.bank_ifsc = bank_ifsc;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
