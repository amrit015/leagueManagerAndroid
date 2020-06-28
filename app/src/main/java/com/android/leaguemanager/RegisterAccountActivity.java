package com.android.leaguemanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.leaguemanager.di.LeagueApp;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageRepository;
import com.android.leaguemanager.mainPage.mainPagePresenter.MainPagePresenterListener;
import com.android.leaguemanager.mainPage.mainPageView.MainPageViewListener;
import com.android.leaguemanager.utils.ApiConstant;
import com.android.leaguemanager.utils.AppAlerts;
import com.android.leaguemanager.utils.HideKeyboard;

import java.util.HashMap;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener, MainPageViewListener, TextView.OnEditorActionListener {

    TextView tvToolbar;
    ImageView imgBack;
    Button btRegister;
    EditText etFirstName, etLastName, etUsername, etEmail, etPassword, etConfirmPassword;
    ConstraintLayout layoutMain, layoutProgress;

    @Inject
    MainPagePresenterListener presenterListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        ((LeagueApp) getApplication()).getApplicationComponent().inject(this);
        tvToolbar = findViewById(R.id.toolbar_title);
        imgBack = findViewById(R.id.toolbar_back);
        btRegister = findViewById(R.id.bt_register);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        layoutMain = findViewById(R.id.layout_main);
        layoutProgress = findViewById(R.id.layout_progress);
        etConfirmPassword.setOnEditorActionListener(this);
        tvToolbar.setText(getResources().getString(R.string.create_account));
        imgBack.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterListener.setView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                onBackPressed();
                break;
            case R.id.bt_register:
                checkRequirementsForAccountCreation();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        checkRequirementsForAccountCreation();
        return false;
    }

    private void checkRequirementsForAccountCreation() {
        /*
            - check if fields are empty
            - check requirements for email address
            - confirm password
         */

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String userName = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (checkAllFieldsForEmpty(firstName, lastName, userName, email, password, confirmPassword)) {
            if (isValidEmail(email)) {
                if (isPasswordValid(password)) {
                    if (isPasswordConfirmed(password, confirmPassword)) {
                        // all success
                        performRegisterAccount(firstName, lastName, userName, email, password);
                    } else {
                        // passwords doesn't match
                        hideKeyboard();
                        showSnackBar(getResources().getString(R.string.password_not_match));
                    }
                } else {
                    // password isn't at least 8 characters
                    hideKeyboard();
                    showSnackBar(getResources().getString(R.string.password_requirement));
                }
            } else {
                // email isn't valid
                hideKeyboard();
                showSnackBar(getResources().getString(R.string.email_requirement));
            }
        } else {
            // empty fields
            hideKeyboard();
            showSnackBar(getResources().getString(R.string.empty_field));
        }

    }

    private void performRegisterAccount(String firstName, String lastName, String userName, String email, String password) {
        HashMap<String, String> authAccount = new HashMap<>();
        authAccount.put(ApiConstant.USERNAME, userName);
        authAccount.put(ApiConstant.PASSWORD, password);
        authAccount.put(ApiConstant.FIRST_NAME, firstName);
        authAccount.put(ApiConstant.LAST_NAME, lastName);
        authAccount.put(ApiConstant.EMAIL, email);
        presenterListener.performRegisterAccount(authAccount);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        HideKeyboard.hideKeyboardFromWindow(view, RegisterAccountActivity.this);
    }

    private void showSnackBar(String message) {
        AppAlerts.showSnackBar(layoutMain, message);
    }

    private boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    private boolean checkAllFieldsForEmpty(String firstName, String lastName, String userName, String email, String password, String confirmPassword) {
        return firstName.length() > 0 && lastName.length() > 0 && userName.length() > 0 && email.length() > 0 && password.length() > 0 && confirmPassword.length() > 0;
    }

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    public void onResult(MainPageRepository repository) {
        // login after register - get the saved username and password

    }

    @Override
    public void onMainView(int visibility) {
        layoutMain.setVisibility(visibility);
    }

    @Override
    public void onProgressView(int visibility) {
        layoutProgress.setVisibility(visibility);
    }

    @Override
    public void onButtonEnabled(boolean enable) {
        btRegister.setEnabled(enable);
    }
}
