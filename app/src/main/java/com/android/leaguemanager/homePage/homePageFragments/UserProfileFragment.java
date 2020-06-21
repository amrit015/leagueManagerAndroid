package com.android.leaguemanager.homePage.homePageFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.leaguemanager.R;
import com.android.leaguemanager.di.GlideApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserProfileFragment extends Fragment {

    ImageView imgProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        TextView tvToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        imgProfile = rootView.findViewById(R.id.img_profile);
        tvToolbarTitle.setText(getResources().getString(R.string.profile));
        return rootView;
    }
}
