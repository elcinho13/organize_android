package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controlers.FirstAccessControler;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @Bind(R.id.txtLoading)
    TextView txtLoading;
    private Context context;
    private String locale;
    private String device_id;
    private FirstAccess firstAccess;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SPLASH");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        context = SplashActivity.this;
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        locale = Locale.getDefault().toString();


    }

    @Override
    protected void onResume(){
        super.onResume();
        getFirstAccess();
    }

    protected void getFirstAccess() {
        new FirstAccessControler(context).getFirstAccess(device_id, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    firstAccess = (FirstAccess) object;
                    if (firstAccess.getLocale().equals(locale)){
                        verifyData();
                    }
                    else{
                        editLocale();
                    }
                } else {
                    firstAccess = new FirstAccess();
                    setFirstAccess();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void editLocale(){
        new FirstAccessControler(context).updateLocale(firstAccess, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null){
                    firstAccess = (FirstAccess) object;
                    verifyData();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void setFirstAccess() {
        user = new User();
        firstAccess.setUser(user);
        firstAccess.setDevice_id(device_id);
        firstAccess.setLocale(locale);
        firstAccess.setInstalation_date(new Date());

        PreferencesManager.saveFirstAccess(firstAccess);
        startApresentationActivity();
    }

    protected void verifyData() {
        user = firstAccess.getUser();
        if (user.getUser_term() == null || !user.getUser_term().isTerm_accept()) {
            startApresentationActivity();
        } else if (user.getPlan() == null) {
            startPlanIdentifierActivity();
        } else if (user.getUser_type() == null) {
            startUserRegisterActivity();
        } else if (user.getToken() == null || !user.getToken().isKeep_logged() || !PreferencesManager.isLogged()) {
            starLoginActivity();
        } else if (PreferencesManager.isHideWelcome()) {
            starHomeActivity();
        } else {
            starWelcomeActivity();
        }
    }

    protected void startApresentationActivity() {
        Intent intent = new Intent(context, ApresentationActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void startPlanIdentifierActivity() {
        Intent intent = new Intent(context, PlanIdentifierActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void startUserRegisterActivity() {
        Intent intent = new Intent(context, UserRegisterActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void starLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void starWelcomeActivity() {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void starHomeActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
