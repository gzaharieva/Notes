package com.parse.notes;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

/**
 * @author gzaharieva
 */
public class ParseApplication extends Application {

    private static ParseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
//        Parse.initialize(this);

        Parse.initialize(this, BuildConfig.PARSE_APP_ID, BuildConfig.PAERSE_CLIENT_KEY);
    }

    public static ParseApplication getInstance() {
        return instance;
    }
}
