package com.android.leaguemanager.di;

import android.content.SharedPreferences;

import com.android.leaguemanager.MainActivity;
import com.android.leaguemanager.RegisterAccountActivity;
import com.android.leaguemanager.homePage.homePageFragments.UserProfileFragment;
import com.android.leaguemanager.utils.ApiClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MainPageModule.class, ApiClient.class, SharedPreferenceModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(UserProfileFragment userProfileFragment);

    void inject(RegisterAccountActivity registerAccountActivity);
}
