package com.android.leaguemanager.mainPage.mainPageView;

import com.android.leaguemanager.mainPage.mainPageModel.MainPageRepository;

public interface MainPageViewListener {

    void onResult(MainPageRepository repository);

    void onMainView(int visibility);

    void onProgressView(int visibility);

    void onButtonEnabled(boolean enable);
}
