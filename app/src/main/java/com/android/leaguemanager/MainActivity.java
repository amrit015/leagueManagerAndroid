package com.android.leaguemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.leaguemanager.di.LeagueApp;
import com.android.leaguemanager.homePage.HomeActivity;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageRepository;
import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponseData;
import com.android.leaguemanager.mainPage.mainPagePresenter.MainPagePresenterListener;
import com.android.leaguemanager.mainPage.mainPageView.MainPageViewListener;
import com.android.leaguemanager.utils.ApiConstant;
import com.android.leaguemanager.utils.AppAlerts;
import com.android.leaguemanager.utils.AppLogs;
import com.android.leaguemanager.utils.HideKeyboard;
import com.android.leaguemanager.utils.SharedPreferenceHelper;

import java.util.HashMap;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainPageViewListener, TextView.OnEditorActionListener {

    private static final String TAG = "MainActivity";
    private EditText etPassword;
    private EditText etUsername;
    private ConstraintLayout layoutMain;
    private ConstraintLayout layoutProgress;
    private Button btLogin;

    @Inject
    MainPagePresenterListener presenterListener;

    @Inject
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((LeagueApp) getApplication()).getApplicationComponent().inject(this);

        etPassword = findViewById(R.id.et_password);
        etUsername = findViewById(R.id.et_username);
        layoutMain = findViewById(R.id.layout_main);
        layoutProgress = findViewById(R.id.layout_progress);
        TextView tvRegister = findViewById(R.id.tv_register);
        btLogin = findViewById(R.id.bt_login);
        tvRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        etPassword.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterListener.setView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        performLogin();
        return false;
    }

    private void performLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (username.length() > 0 && password.length() > 0) {
            // send the json with the username and the password to the server
            HashMap<String, String> authData = new HashMap<>();
            authData.put(ApiConstant.USERNAME, username);
            authData.put(ApiConstant.PASSWORD, password);
            presenterListener.performLoginValidation(authData);
            startOfOperation();
        } else {
            hideKeyboard();
            showSnackBar(getResources().getString(R.string.empty_field));
        }
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        HideKeyboard.hideKeyboardFromWindow(view, MainActivity.this);
    }

    private void showSnackBar(String message){
        AppAlerts.showSnackBar(layoutMain, message);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterAccountActivity.class);
        startActivity(intent);
    }

    private void startOfOperation() {
        presenterListener.setMainView(View.INVISIBLE);
        presenterListener.setProgressBar(View.VISIBLE);
        presenterListener.setButtonEnabled(false);
    }

    private void endOfOperation() {
        presenterListener.setMainView(View.VISIBLE);
        presenterListener.setProgressBar(View.INVISIBLE);
        presenterListener.setButtonEnabled(true);
    }

    @Override
    public void onResult(MainPageRepository repository) {
        // response after the authentication callback
        hideKeyboard();

        if (repository.isStatus()) {
            // success

            // show the success toast
            String message = repository.getServerMessage();
            if (message != null && message.length() > 0) {
                AppAlerts.showToast(MainActivity.this, message);
            }

            // save the username for other operations
            LoginResponseData loginResponseData = repository.getLoginResponseData();
            if (loginResponseData != null) {
                String username = loginResponseData.getUsername();
                String accessToken = loginResponseData.getAccessToken();

                if (username != null && username.length() > 0) {
                    AppLogs.i(TAG, "Username : " + username);
                    sharedPreferenceHelper.setUsername(username);
                }

                if (accessToken != null && accessToken.length() > 0) {
                    sharedPreferenceHelper.setAccessToken(accessToken);
                }
            }

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // failure
            showSnackBar(repository.getServerMessage());
        }

        endOfOperation();
    }

    @Override
    public void onMainView(int visibility) {
        btLogin.setVisibility(visibility);
    }

    @Override
    public void onProgressView(int visibility) {
        layoutProgress.setVisibility(visibility);
    }

    @Override
    public void onButtonEnabled(boolean enable) {
        btLogin.setEnabled(enable);
    }
}