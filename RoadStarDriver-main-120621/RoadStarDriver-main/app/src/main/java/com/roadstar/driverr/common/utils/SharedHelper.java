package com.roadstar.driverr.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedHelper {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.apply();

    }

    public static void putBoolean(Context context, String Key, boolean Value) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(Key, Value);
        editor.apply();
    }
    public static boolean getBoolean(Context context, String Key) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Key,false);
    }


    public static void putKey(Context context, String Key, Class<?> value) {

        Gson gson = new Gson();
        String jsonObject = gson.toJson(value);

        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, jsonObject);
        editor.apply();

    }

    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "");
        return Value;
    }

    public static void  putKey(Context context, String serializedObjectKey, Object classType) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(classType);
        editor.putString(serializedObjectKey, serializedObject);
        editor.apply();
    }

    public static <GenericClass> GenericClass getKey(Context context, String preferenceKey, Class<GenericClass> classType) {

        try {
            if (sharedPreferences != null){
                sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
                if (sharedPreferences.contains(preferenceKey)) {
                    final Gson gson = new Gson();
                    return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
                }
            }
        }catch (Exception e){}
        return null;
    }


    public static void clearSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        //sharedPreferences.edit().clear().commit();

    }


}