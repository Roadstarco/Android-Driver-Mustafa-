package com.roadstar.driverr.app.network;

import com.roadstar.driverr.app.data.models.ChangePasswrodModel;
import com.roadstar.driverr.app.data.models.CombineHistoryModel;
import com.roadstar.driverr.app.data.models.StatusChangeModel;
import com.roadstar.driverr.app.data.models.documentModel;
import com.roadstar.driverr.app.data.models.support.SupportMessageModel;
import com.roadstar.driverr.app.data.req.LocationObjectReq;
import com.roadstar.driverr.app.data.req.LoginReq;
import com.roadstar.driverr.app.data.req.RefreshTokenReq;
import com.roadstar.driverr.app.data.req.RegistrationReq;
import com.roadstar.driverr.app.data.req.SubmitRatingReq;
import com.roadstar.driverr.app.data.req.UpdatePackageStatusReq;
import com.roadstar.driverr.app.data.resp.DocumentsListResp;
import com.roadstar.driverr.app.data.resp.RefreshTokenResp;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.data.resp.TotalEarningsSummary;
import com.roadstar.driverr.app.data.resp.UploadDocumentsResp;
import com.roadstar.driverr.app.data.resp.UserProfileResp;
import com.roadstar.driverr.app.module.ui.request.model.BidModel;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;
import com.roadstar.driverr.app.module.ui.request.model.UpdateStatusModel;
import com.roadstar.driverr.app.module.ui.your_package.model.AcceptUserBid;
import com.roadstar.driverr.app.module.ui.your_package.model.AllProviderBidsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("api/provider/profile")
    Call<UserProfileResp> getUserProfile(@Query("device_type") String device_type,
                                         @Query("device_id") String device_id,
                                         @Query("device_token") String device_token);



    @POST("api/provider/oauth/token")
    Call<UserProfileResp> loginRequest(@Body LoginReq loginReq);

    @POST("api/provider/oauth/token")
    Call<RefreshTokenResp> refreshToken(@Body RefreshTokenReq refreshTokenReq);

    @POST("api/provider/register")
    Call<UserProfileResp> registerRequest(@Body RegistrationReq registrationReq);


    @GET("api/provider/documents/listing")
    Call<ArrayList<DocumentsListResp>> getDocumentList();

    @POST("api/provider/documents/upload")
    Call<UploadDocumentsResp> uploadDocuments(@Body RequestBody surveyImage);

    @GET("api/provider/trip")
    Call<RequestsModel> checkRequestStatus(@Query("latitude") String latitude,
                                           @Query("longitude") String longitude);

    @GET("api/provider/trip")
    Call<RequestsModel> checkRequestStatus();

    @GET("api/provider/airports")
    Call<ArrayList<DocumentsListResp>> getairport(@Query("searchTerm") String searchTerm);

    @GET("api/provider/requests/history")
    Call<CombineHistoryModel> getHistory();

    @POST("api/provider/summary")
    Call<TotalEarningsSummary> getTotalEarnings();

    @POST("api/provider/trip/{id}")
    Call<String> acceptRequest(@Path("id") String id);

    @POST("api/provider/trip/{id}/calculate")
    Call<ResponseBody> getLiveTracking(@Path("id") String id, @Body LocationObjectReq latLongObject);

    @POST("api/provider/trip/{id}")
    Call<Request> updatePackageStatus(@Path("id") String id, @Body UpdatePackageStatusReq updatePackageStatusReq);

    @POST("api/provider/trip/{id}/rate")
    Call<String> submitRating(@Path("id") String id, @Body SubmitRatingReq submitRatingReq);

    @POST("api/provider/bid-user-trip")
    Call<ResponseBody> bidOnUserRequest(@Body BidModel values/*, @Header("Host")String host, @Header("Content-Type")String contentType*/);

    @POST("api/provider/update-trip")
    Call<ResponseBody> updateStatus(@Body UpdateStatusModel values/*, @Header("Host")String host, @Header("Content-Type")String contentType*/);

    @POST("api/provider/rate-trip-user")
    Call<ResponseBody> submitRatingInternational( @Body SubmitRatingReq submitRatingReq);

    @POST("api/provider/provider-trips")
    Call<ArrayList<InternationTripModel>> getAllScheduledTrips();

    @POST("api/provider/post-trip")
    Call<ResponseBody> postTrip( @Body RequestBody requestBody);

    @POST("api/provider/trip-bids")
    Call<ArrayList<AllProviderBidsModel>> providerTripBids(@Body AllProviderBidsModel model);

    @POST("api/provider/update-bid")
    Call<ResponseBody> acceptBid(@Body AcceptUserBid model);

    @POST("api/provider/bid/counter-accept")
    Call<ResponseBody> AcceptCounterOffer(@Body AcceptUserBid model);

    @POST("api/provider/bid/counter-reject")
    Call<ResponseBody> retjectCounterOffer(@Body AcceptUserBid model);

    @POST("api/provider/bid/counter-offer")
    Call<ResponseBody> addCounterOffer(@Body AcceptUserBid model);

    @POST("api/provider/profile")
    Call<ResponseBody> updateProfile(@Body RequestBody userProfileResp);

    @POST("api/provider/profile/password")
    Call<ResponseBody> changePassword(@Body ChangePasswrodModel changePasswrodModel);

    @POST("api/provider/support/message")
    Call<ResponseBody> sendSupportMessage(@Body SupportMessageModel supportMessageModel);

    @GET("api/provider/documents/display-provider-documents")
    Call<ArrayList<documentModel>> getDocumentListAndDisplay();


    @POST("api/provider/profile/available")
    Call<ResponseBody> changeOnlineStatus(@Body StatusChangeModel body);

    @POST("api/provider/profile/update/profile/fcm")
    @FormUrlEncoded
    Call<JSONObject> setFcm( @Field("fcm") String type);
}
