package com.android.leaguemanager.homePage.homePageFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.leaguemanager.R;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageRepository;
import com.android.leaguemanager.mainPage.mainPagePresenter.MainPagePresenterListener;
import com.android.leaguemanager.mainPage.mainPageView.MainPageViewListener;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserHomeFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_home, container, false);
        TextView tvToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        tvToolbarTitle.setText(getResources().getString(R.string.home));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
