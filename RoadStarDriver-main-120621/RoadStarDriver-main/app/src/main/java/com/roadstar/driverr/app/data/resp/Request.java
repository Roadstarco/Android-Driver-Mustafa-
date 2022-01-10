package com.roadstar.driverr.app.data.resp;

import com.google.gson.annotations.SerializedName;
import com.roadstar.driverr.app.business.BaseItem;

import java.io.Serializable;

public class Request implements BaseItem, Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("tripto")
    private String tripto;


    @SerializedName("tripfrom")
    private String tripfrom;
/*
    @SerializedName("provider_id")
    private Integer provider_id;*/


    public String getTripfrom() {
        return tripfrom;
    }

    public void setTripfrom(String tripfrom) {
        this.tripfrom = tripfrom;
    }

/*
    public Integer getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Integer provider_id) {
        this.provider_id = provider_id;
    }
*/

    public String getTripto() {
        return tripto;
    }

    public void setTripto(String tripto) {
        this.tripto = tripto;
    }

    @SerializedName("booking_id")
    private String bookingId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("provider_id")
    private String providerId;

    @SerializedName("current_provider_id")
    private String currentProviderId;

    @SerializedName("service_type_id")
    private String serviceTypeId;

    @SerializedName("status")
    private String status;

    @SerializedName("cancelled_by")
    private String cancelledBy;

    @SerializedName("cancel_reason")
    private String cancelReason;

    @SerializedName("payment_mode")
    private String paymentMode;

    @SerializedName("paid")
    private String paid;

    @SerializedName("is_track")
    private String isTrack;

    @SerializedName("distance")
    private float distance;

    @SerializedName("travel_time")
    private String travelTime;

    @SerializedName("s_address")
    private String sAddress;

    @SerializedName("s_latitude")
    private String sLatitude;

    @SerializedName("s_longitude")
    private String sLongitude;

    @SerializedName("d_address")
    private String dAddress;

    @SerializedName("d_latitude")
    private String dLatitude;

    @SerializedName("track_distance")
    private String trackDistance;

    @SerializedName("track_latitude")
    private String trackLatitude;

    @SerializedName("track_longitude")
    private String trackLongitude;

    @SerializedName("d_longitude")
    private String dLongitude;

    @SerializedName("assigned_at")
    private String assignedAt;

    @SerializedName("schedule_at")
    private String scheduleAt;

    @SerializedName("started_at")
    private String startedAt;

    @SerializedName("finished_at")
    private String finishedAt;

    @SerializedName("user_rated")
    private String userRated;

    @SerializedName("provider_rated")
    private String providerRated;

    @SerializedName("use_wallet")
    private String useWallet;


    @SerializedName("surge")
    private String surge;

    @SerializedName("route_key")
    private String routeKey;

    @SerializedName("deleted_at")
    private String deletedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user")
    private UserModel userModel;

    @SerializedName("payment")
    private Payment payment;


    @SerializedName("category")
    private String category;

    @SerializedName("product_type")
    private String product_type;

    @SerializedName("product_width")
    private String product_width;

    @SerializedName("product_weight")
    private String product_weight;

    @SerializedName("product_height")
    private String product_height;

    @SerializedName("instruction")
    private String instruction;

    @SerializedName("receiver_name")
    private String receiver_name;

    @SerializedName("receiver_phone")
    private String receiver_phone;

    @SerializedName("attachment1")
    private String attachment1;

    @SerializedName("attachment2")
    private String attachment2;

    @SerializedName("attachment3")
    private String attachment3;

    @SerializedName("product_distribution")
    private String product_distribution;

    @SerializedName("weight_unit")
    private String weight_unit;

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public String getProduct_description() {
        return product_distribution;
    }

    public void setProduct_description(String product_description) {
        this.product_distribution = product_description;
    }

    public String getProduct_width() {
        return product_width;
    }

    public void setProduct_width(String product_width) {
        this.product_width = product_width;
    }

    public String getProduct_height() {
        return product_height;
    }

    public void setProduct_height(String product_height) {
        this.product_height = product_height;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_number() {
        return receiver_phone;
    }

    public void setReceiver_number(String receiver_number) {
        this.receiver_phone = receiver_number;
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1;
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2;
    }

    public String getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(String attachment3) {
        this.attachment3 = attachment3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCurrentProviderId() {
        return currentProviderId;
    }

    public void setCurrentProviderId(String currentProviderId) {
        this.currentProviderId = currentProviderId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getIsTrack() {
        return isTrack;
    }

    public void setIsTrack(String isTrack) {
        this.isTrack = isTrack;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsLatitude() {
        return sLatitude;
    }

    public void setsLatitude(String sLatitude) {
        this.sLatitude = sLatitude;
    }

    public String getsLongitude() {
        return sLongitude;
    }

    public void setsLongitude(String sLongitude) {
        this.sLongitude = sLongitude;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getdLatitude() {
        return dLatitude;
    }

    public void setdLatitude(String dLatitude) {
        this.dLatitude = dLatitude;
    }

    public String getTrackDistance() {
        return trackDistance;
    }

    public void setTrackDistance(String trackDistance) {
        this.trackDistance = trackDistance;
    }

    public String getTrackLatitude() {
        return trackLatitude;
    }

    public void setTrackLatitude(String trackLatitude) {
        this.trackLatitude = trackLatitude;
    }

    public String getTrackLongitude() {
        return trackLongitude;
    }

    public void setTrackLongitude(String trackLongitude) {
        this.trackLongitude = trackLongitude;
    }

    public String getdLongitude() {
        return dLongitude;
    }

    public void setdLongitude(String dLongitude) {
        this.dLongitude = dLongitude;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(String assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getScheduleAt() {
        return scheduleAt;
    }

    public void setScheduleAt(String scheduleAt) {
        this.scheduleAt = scheduleAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getUserRated() {
        return userRated;
    }

    public void setUserRated(String userRated) {
        this.userRated = userRated;
    }

    public String getProviderRated() {
        return providerRated;
    }

    public void setProviderRated(String providerRated) {
        this.providerRated = providerRated;
    }

    public String getUseWallet() {
        return useWallet;
    }

    public void setUseWallet(String useWallet) {
        this.useWallet = useWallet;
    }

    public String getSurge() {
        return surge;
    }

    public void setSurge(String surge) {
        this.surge = surge;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public int getItemType() {
        return BaseItem.ITEM_HISTORY;
    }
}
