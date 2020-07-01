package com.android.leaguemanager.utils;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEndpoints {

    @POST(ApiConstant.LOGIN)
    Observable<LoginResponse> authLogin(@Body HashMap authRequest);

    @POST(ApiConstant.REGISTER)
    Observable<RegisterResponse> registerAccount(@Body HashMap accountRequest);
}
