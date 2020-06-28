package com.android.leaguemanager.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferenceHelper {

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setUsername(String username){
        sharedPreferences.edit().putString(AppKeys.KEY_USERNAME, username).apply();
    }

    public String getUsername(){
        return sharedPreferences.getString(AppKeys.KEY_USERNAME, "");
    }

    public void setAccessToken(String accessToken){
        sharedPreferences.edit().putString(AppKeys.KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken(){
        return sharedPreferences.getString(AppKeys.KEY_ACCESS_TOKEN, "");
    }
}
