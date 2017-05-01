package com.organize4event.organize.controllers;

import android.content.Context;

import com.organize4event.organize.commons.ApiClient;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.services.PrivacyService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PrivacyControll extends Controll {
    public PrivacyControll(Context context) {
        super(context);
    }

    public void getPrivacy(String locale, final ControllResponseListener listener){
        PrivacyService service = ApiClient.getRetrofit().create(PrivacyService.class);
        service.getPrivacy(locale).enqueue(new Callback<ArrayList<Privacy>>() {
            @Override
            public void onResponse(Response<ArrayList<Privacy>> response, Retrofit retrofit) {
                ArrayList<Privacy> privacies = (ArrayList<Privacy>) response.body();
                Error error = parserError(privacies.get(0));
                if (error == null){
                    listener.success(privacies);
                }
                else {
                    listener.fail(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.fail(new Error(t.getMessage()));
            }
        });
    }
}
