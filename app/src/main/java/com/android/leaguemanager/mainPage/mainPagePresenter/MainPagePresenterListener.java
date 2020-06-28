package com.android.leaguemanager.mainPage.mainPagePresenter;

import com.android.leaguemanager.mainPage.mainPageView.MainPageViewListener;

import java.util.HashMap;

public interface MainPagePresenterListener {

    void setView(MainPageViewListener view);

    void performLoginValidation(HashMap authRequest);

    void performRegisterAccount(HashMap accountRequest);

    void setProgressBar(int visibility);

    void setMainView(int visibility);

    void setButtonEnabled(boolean enable);

    void disposeOperation();
}
