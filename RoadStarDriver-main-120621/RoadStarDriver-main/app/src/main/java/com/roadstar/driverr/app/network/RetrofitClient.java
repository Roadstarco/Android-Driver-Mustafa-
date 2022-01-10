package com.roadstar.driverr.app.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.roadstar.driverr.common.utils.ApiConstants.ACCESS_TOKEN;


public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://myroadstar.org/")
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " +ACCESS_TOKEN)
                .addHeader("X-Requested-With", "XMLHttpRequest");

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }).addInterceptor(getInterceptor()).build();


    private static HttpLoggingInterceptor getInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return  interceptor;
    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                //.cache(new Cache(MvpApplication.getInstance().getCacheDir(), 10 1024 1024)) // 10 MB
                .connectTimeout(10, TimeUnit.MINUTES)
              /*  .addNetworkInterceptor(new AddHeaderInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
              */  .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();
    }

}
