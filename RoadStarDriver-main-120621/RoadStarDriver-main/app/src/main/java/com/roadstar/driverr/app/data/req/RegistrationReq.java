package com.roadstar.driverr.app.data.req;

public class RegistrationReq {

    private  String device_type;
    private String device_id;
    private String device_token;
    private String email;
    private String password;
    private String password_confirmation;
    private String first_name;
    private String last_name;
    private String login_by;
    private String mobile;
    private String home_address;
    private String comp_name = "";
    private String comp_reg_no = "";
    private String number_of_vehicle = "";


    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_reg_no() {
        return comp_reg_no;
    }

    public void setComp_reg_no(String comp_reg_no) {
        this.comp_reg_no = comp_reg_no;
    }

    public String getNumber_of_vehicle() {
        return number_of_vehicle;
    }

    public void setNumber_of_vehicle(String number_of_vehicle) {
        this.number_of_vehicle = number_of_vehicle;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
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

    public String getLogin_by() {
        return login_by;
    }

    public void setLogin_by(String login_by) {
        this.login_by = login_by;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
