package com.android.leaguemanager.mainPage.mainPageModel;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponseData;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;

public class MainPageRepository {

    private boolean isStatus;
    private String serverMessage;
    private LoginResponseData loginResponseData;

    public MainPageRepository() {
    }

    public MainPageRepository(boolean status, String message, LoginResponseData loginResponseData) {
        this.isStatus = status;
        this.serverMessage = message;
        this.loginResponseData = loginResponseData;
    }

    public MainPageRepository sendAuthenticationAction(LoginResponse loginResponse) {
        boolean status = checkStatus(loginResponse.getStatus());
        String message = loginResponse.getMessage();
        LoginResponseData responseData = loginResponse.getLoginResponseData();
        return new MainPageRepository(status, message, responseData);
    }

    public MainPageRepository sendAccountCreationAction(RegisterResponse registerResponse) {
        boolean status = checkStatus(registerResponse.getStatus());
        String message = registerResponse.getMessage();
        return new MainPageRepository(status, message, null);
    }

    private boolean checkStatus(int status) {
        return status == 200;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public LoginResponseData getLoginResponseData() {
        return loginResponseData;
    }

    public void setLoginResponseData(LoginResponseData loginResponseData) {
        this.loginResponseData = loginResponseData;
    }
}
