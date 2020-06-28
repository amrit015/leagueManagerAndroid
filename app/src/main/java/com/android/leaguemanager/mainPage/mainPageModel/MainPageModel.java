package com.android.leaguemanager.mainPage.mainPageModel;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;
import com.android.leaguemanager.utils.NetworkApiCalls;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MainPageModel implements MainPageModelListener {

    NetworkApiCalls networkCall;

    @Inject
    public MainPageModel(NetworkApiCalls networkCall) {
        this.networkCall = networkCall;
    }

    @Override
    public Observable<LoginResponse> initLoginValidation(HashMap authRequest) {
        return networkCall.postAuthRequest(authRequest);
    }

    @Override
    public Observable<RegisterResponse> initRegisterAccount(HashMap accountRequest) {
        return networkCall.postRegisterAccount(accountRequest);
    }
}
