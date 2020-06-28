package com.android.leaguemanager.mainPage.mainPageModel;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public interface MainPageModelListener {

    Observable<LoginResponse> initLoginValidation(HashMap authRequest);

    Observable<RegisterResponse> initRegisterAccount(HashMap accountRequest);
}
