package com.roadstar.driverr.common.utils;

public class ApiConstants {
    /**
     * Create a full server rest api url by embedding provide end point with the server url
     *
     * @param api Rest api endpoint
     * @return Full rest api url
     */
    public static String getServerUrl(String api) {
        return BASE_URL + api;
    }

    //Api Url

    public static final String BASE_URL = "https://myroadstar.org/";

    public static final String UPLOAD_PROFILE_IMAGE = "users/uploadUserPicture/picture";
    public static final String UPLOAD_PLAYLIST_PIC = "playlists/upload/picture";


    //Api Param Keya
    public static final int LIMIT = 10;
    public static String ACCESS_TOKEN = "access_token";
    public static String REFRESH_TOKEN = "refresh_token";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_API_KEY = "apiKey";
    public static String DEVICE_ID = "device_id";
    public static String DEVICE_TYPE = "android";
    public static String DEVICE_TOKEN = "device_token";
    public static String CLIENT_SECRET = "WX2IZR5Yi6gpZ3ajSJ4meKik3R0K1z2vomJVc2Qw";
    public static String CLIENT_ID = "2";
    public static String GRANT_TYPE = "refresh_token";



    public static final String HEADER_BEARER = "Bearer ";
    public static final String HEADER_KEY_XMLHTTP = "";
    public static final String BID_USER_TRIP="api/provider/bid-user-trip";

    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ID = "id";
    public static final String KEY_CONFIRM_PASS = "password_confirmation";
    public static final String RESET_PASSWORD_URL = "api/provider/reset/password";




}
