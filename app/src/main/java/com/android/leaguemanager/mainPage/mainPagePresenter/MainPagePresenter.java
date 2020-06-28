package com.android.leaguemanager.mainPage.mainPagePresenter;

import com.android.leaguemanager.MainActivity;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageModelListener;
import com.android.leaguemanager.mainPage.mainPageModel.MainPageRepository;
import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;
import com.android.leaguemanager.mainPage.mainPageView.MainPageViewListener;
import com.android.leaguemanager.utils.DisplayText;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainPagePresenter implements MainPagePresenterListener {

    private MainPageModelListener modelListener;
    private MainPageRepository repository;
    private MainPageViewListener viewListener;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public MainPagePresenter(MainPageModelListener model, MainPageRepository repository) {
        this.modelListener = model;
        this.repository = repository;
    }

    @Override
    public void setView(MainPageViewListener view) {
        this.viewListener = view;
    }

    @Override
    public void performLoginValidation(HashMap authRequest) {
        Observable<LoginResponse> result = modelListener.initLoginValidation(authRequest);
        disposables.add(result.subscribeWith(new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                viewListener.onResult(repository.sendAuthenticationAction(loginResponse));
            }

            @Override
            public void onError(Throwable e) {
                viewListener.onResult(new MainPageRepository(false, DisplayText.FAILURE_LOGIN, null));
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void performRegisterAccount(HashMap accountRequest) {
        Observable<RegisterResponse> result = modelListener.initRegisterAccount(accountRequest);
        disposables.add(result.subscribeWith(new DisposableObserver<RegisterResponse>() {
            @Override
            public void onNext(RegisterResponse registerResponse) {
                viewListener.onResult(repository.sendAccountCreationAction(registerResponse));
            }

            @Override
            public void onError(Throwable e) {
                viewListener.onResult(new MainPageRepository(false, DisplayText.FAILURE_REGISTER_ACCOUNT, null));
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void setProgressBar(int visibility) {
        viewListener.onProgressView(visibility);
    }

    @Override
    public void setMainView(int visibility) {
        viewListener.onMainView(visibility);
    }

    @Override
    public void setButtonEnabled(boolean enable) {
        viewListener.onButtonEnabled(enable);
    }

    @Override
    public void disposeOperation() {
        disposables.clear();
        disposables.dispose();
    }
}
