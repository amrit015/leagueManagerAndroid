package com.android.leaguemanager.di;

import com.android.leaguemanager.mainPage.mainPageModel.MainPageModel;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageModelListener;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageRepository;
import com.android.leaguemanager.mainPage.mainPagePresenter.MainPagePresenter;
import com.android.leaguemanager.mainPage.mainPagePresenter.MainPagePresenterListener;
import com.android.leaguemanager.utils.NetworkApiCalls;

import dagger.Module;
import dagger.Provides;

@Module
public class MainPageModule {

    @Provides
    MainPagePresenterListener provideMainPagePresenter(MainPageModel model, MainPageRepository repo) {
        return new MainPagePresenter(model, repo);
    }

    @Provides
    MainPageModelListener provideMainPageModel(NetworkApiCalls networkCall) {
        return new MainPageModel(networkCall);
    }

    @Provides
    NetworkApiCalls provideNetworkCalls(){
        return new NetworkApiCalls();
    }

    @Provides
    MainPageRepository provideMainPageRepo() {
        return new MainPageRepository();
    }
}
