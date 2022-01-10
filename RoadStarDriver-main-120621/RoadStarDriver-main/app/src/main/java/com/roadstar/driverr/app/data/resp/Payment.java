package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;
import com.roadstar.driverr.app.business.BaseItem;

import java.io.Serializable;

public class Payment implements BaseItem, Serializable {


    @SerializedName("fixed")
    private String fixed;


    @SerializedName("distance")
    private String distance;

    @SerializedName("tax")
    private String tax;

    @SerializedName("total")
    private  String total;

    @SerializedName("payable")
    private String payable;

    @SerializedName("commision")
    private String commision;

    @SerializedName("id")
    public String id;

    @SerializedName("request_id")
    public String request_id;

    @SerializedName("promocode_id")
    public String promocode_id;

    @SerializedName("payment_id")
    public String payment_id;

    @SerializedName("payment_mode")
    public String payment_mode;

    @SerializedName("discount")
    public String discount;

    @SerializedName("wallet")
    public String wallet;

    @SerializedName("surge")
    public String surge;

    @SerializedName("provider_commission")
    public String provider_commission;

    @SerializedName("provider_pay")
    public String provider_pay;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getPromocode_id() {
        return promocode_id;
    }

    public void setPromocode_id(String promocode_id) {
        this.promocode_id = promocode_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getSurge() {
        return surge;
    }

    public void setSurge(String surge) {
        this.surge = surge;
    }

    public String getProvider_commission() {
        return provider_commission;
    }

    public void setProvider_commission(String provider_commission) {
        this.provider_commission = provider_commission;
    }

    public String getProvider_pay() {
        return provider_pay;
    }

    public void setProvider_pay(String provider_pay) {
        this.provider_pay = provider_pay;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPayable() {
        return payable;
    }

    public void setPayable(String payable) {
        this.payable = payable;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    @Override
    public int getItemType() {
        return BaseItem.ITEM_HISTORY;
    }
}
