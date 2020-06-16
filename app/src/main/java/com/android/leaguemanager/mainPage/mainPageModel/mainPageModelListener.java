package com.android.leaguemanager.mainPage.mainPageModel;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;

import io.reactivex.Observable;

public interface mainPageModelListener {

    Observable<LoginResponse> initLoginValidation();

    Observable<LoginResponse> initRegisterAccount();
}
