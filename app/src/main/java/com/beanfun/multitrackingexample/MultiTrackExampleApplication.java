package com.beanfun.multitrackingexample;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

public class MultiTrackExampleApplication extends Application {
    private FirebaseAnalytics firebaseAnalytics;
    private AppEventsLogger pixelLogger;

    private static final String AF_DEV_KEY = "gsF3MfCzSExQTVRFne8PmB";

    @Override
    public void onCreate() {
        super.onCreate();
        initAppsFlyer();
        initPixelLogger();
    }

    private void initPixelLogger() {
        pixelLogger = AppEventsLogger.newLogger(this);
    }

    private void initAppsFlyer() {
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                for (String attrName : attributionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + attributionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, this);
        AppsFlyerLib.getInstance().start(this);
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        if (firebaseAnalytics == null) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        }
        return firebaseAnalytics;
    }

    public AppEventsLogger getPixelLogger() {
        if (pixelLogger == null) {
            pixelLogger = AppEventsLogger.newLogger(this);
        }
        return pixelLogger;
    }
}
