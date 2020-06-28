package com.android.leaguemanager.utils;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetworkApiCalls {

    public Observable<LoginResponse> postAuthRequest(HashMap authRequest){
        return ApiClient.performAuthLogin(authRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<RegisterResponse> postRegisterAccount(HashMap accountRequest){
        return ApiClient.performRegisterAccount(accountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
