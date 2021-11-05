package com.beanfun.multitrackingexample;

import android.os.Bundle;

import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.Logger;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.beanfun.multitrackingexample.databinding.ActivityMainBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import android.view.Menu;
import android.view.MenuItem;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;

import java.util.HashMap;
import java.util.Map;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
public class MainActivity extends BaseActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                insertFireBaseTracking();
                insertGTMTracking();
                insertAppsFlyerTracking();
                insertFaceBookPixelTracking();
            }
        });
    }

    private void insertFaceBookPixelTracking() {
        //https://developers.facebook.com/docs/marketing-api/auto-ads/guides/events
        //https://developers.facebook.com/apps/473410100638143/analytics/quickstart/
        pixelLogger.logEvent("facebook_pixel_tracking");
        Bundle parameters = new Bundle();
        parameters.putString("facebook_pixel_tracking_params", "userPixelClick");
        pixelLogger.logEvent("facebook_pixel_tracking", parameters);
    }

    private void insertAppsFlyerTracking() {
        //https://support.appsflyer.com/hc/zh-cn/articles/207032126-%E9%80%82%E7%94%A8%E4%BA%8E%E5%BC%80%E5%8F%91%E4%BA%BA%E5%91%98%E7%9A%84Android-SDK%E9%9B%86%E6%88%90#%E7%AE%80%E4%BB%8B
//        Map<String,Object> eventValues = new HashMap<>();
//        eventValues.put("appsFlyer_tracking_params", "userAppsFlyer");
//        AppsFlyerLib.getInstance().logEvent(getApplicationContext(),"APPSFLYER_TRACKING",eventValues);
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(AFInAppEventParameterName.REVENUE,-200);
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE,"shoes");
        eventValue.put(AFInAppEventParameterName.CONTENT_ID,"4875");
        eventValue.put(AFInAppEventParameterName.CURRENCY,"USD");
        AppsFlyerLib.getInstance().logEvent(getApplicationContext() , "cancel_purchase" , eventValue);
    }

    private void insertGTMTracking() {
        //https://developers.google.com/tag-manager/android/v5
        //https://firebase.google.com/docs/analytics/events?platform=android
        Bundle params = new Bundle();
        params.putString("gtm_tracking_params", "userGTMClick");
        firebaseAnalytics.logEvent("GTM_TRACKING", params);
    }

    private void insertFireBaseTracking() {
        //https://firebase.google.com/docs/analytics/events?platform=android
        Bundle params = new Bundle();
        params.putString("fb_tracking_params", "userFireBaseClick");
        firebaseAnalytics.logEvent("FIREBASE_TRACKING", params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}