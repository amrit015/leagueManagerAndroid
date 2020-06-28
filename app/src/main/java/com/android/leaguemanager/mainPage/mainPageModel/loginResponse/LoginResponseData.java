package com.android.leaguemanager.mainPage.mainPageModel.loginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponseData {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("roles")
    @Expose
    private List<String> responseDataRoles;

    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getResponseDataRoles() {
        return responseDataRoles;
    }

    public void setResponseDataRoles(List<String> responseDataRoles) {
        this.responseDataRoles = responseDataRoles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
