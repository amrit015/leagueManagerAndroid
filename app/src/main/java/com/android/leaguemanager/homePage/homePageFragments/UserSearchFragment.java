package com.android.leaguemanager.homePage.homePageFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.leaguemanager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_search, container, false);
        TextView tvToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        tvToolbarTitle.setText(getResources().getString(R.string.browse));
        return rootView;
    }
}
