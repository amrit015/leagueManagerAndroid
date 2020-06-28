package com.android.leaguemanager.utils;

import com.android.leaguemanager.mainPage.mainPageModel.loginResponse.LoginResponse;
import com.android.leaguemanager.mainPage.mainPageModel.registerResponse.RegisterResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiClient {

    private static Retrofit retrofit = null;
    private static final String TAG = "ApiClient";

    @Provides
    Retrofit provideRetrofitForApi() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // interceptor to print the results from the server
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);
        client.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                request = request
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });
        // retrofit builder
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(ApiConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }

    static Observable<LoginResponse> performAuthLogin(HashMap authRequest) {
        return new ApiClient().provideRetrofitForApi().create(ApiEndpoints.class).authLogin(authRequest);
    }

    static Observable<RegisterResponse> performRegisterAccount(HashMap accountRequest) {
        return new ApiClient().provideRetrofitForApi().create(ApiEndpoints.class).registerAccount(accountRequest);
    }

}
