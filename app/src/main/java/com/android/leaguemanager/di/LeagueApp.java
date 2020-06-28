package com.android.leaguemanager.di;

import android.app.Application;

public class LeagueApp extends Application {

    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .mainPageModule(new MainPageModule())
                .sharedPreferenceModule(new SharedPreferenceModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
