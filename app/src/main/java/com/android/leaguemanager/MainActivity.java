package com.android.leaguemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.leaguemanager.homePage.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRegister = findViewById(R.id.tv_register);
        btLogin = findViewById(R.id.bt_login);
        tvRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                openRegisterActivity();
                break;
            case R.id.bt_login:
                performLogin();
                break;
            default:
                break;
        }
    }

    private void performLogin() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterAccountActivity.class);
        startActivity(intent);
    }
}