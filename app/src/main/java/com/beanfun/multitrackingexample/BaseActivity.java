package com.beanfun.multitrackingexample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Logger;
import com.google.firebase.analytics.FirebaseAnalytics;

public class BaseActivity extends AppCompatActivity {
    FirebaseAnalytics firebaseAnalytics;
    AppEventsLogger pixelLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAnalytics = ((MultiTrackExampleApplication)getApplication()).getFirebaseAnalytics();
        pixelLogger =  ((MultiTrackExampleApplication)getApplication()).getPixelLogger();
    }
}
