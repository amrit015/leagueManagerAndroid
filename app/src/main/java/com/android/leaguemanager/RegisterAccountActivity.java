package com.android.leaguemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvToolbar;
    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        tvToolbar = findViewById(R.id.toolbar_title);
        imgBack = findViewById(R.id.toolbar_back);
        tvToolbar.setText(getResources().getString(R.string.create_account));
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
