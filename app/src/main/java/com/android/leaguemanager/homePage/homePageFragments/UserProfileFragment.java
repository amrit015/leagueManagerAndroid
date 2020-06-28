package com.android.leaguemanager.homePage.homePageFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.leaguemanager.R;
import com.android.leaguemanager.di.LeagueApp;
import com.android.leaguemanager.utils.SharedPreferenceHelper;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserProfileFragment extends Fragment {

    ImageView imgProfile;
    TextView tvUsername;

    @Inject
    SharedPreferenceHelper sharedPreferenceHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        if (getActivity() != null) {
            ((LeagueApp) getActivity().getApplication()).getApplicationComponent().inject(this);
        }
        TextView tvToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        imgProfile = rootView.findViewById(R.id.img_profile);
        tvUsername = rootView.findViewById(R.id.tv_profile_name);
        tvToolbarTitle.setText(getResources().getString(R.string.profile));
        loadUsername();
        return rootView;
    }

    private void loadUsername() {
        if (sharedPreferenceHelper.getUsername().length() > 0) {
            tvUsername.setText(sharedPreferenceHelper.getUsername());
        }
    }
}
