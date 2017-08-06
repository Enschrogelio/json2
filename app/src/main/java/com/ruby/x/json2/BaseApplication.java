package com.ruby.x.json2;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.ruby.x.json2.Helpers.GlobalHelper;

public class BaseApplication extends Application {
    private static Context appsContext;
    private static BaseApplication instances;
    private static GlobalHelper globalHelper;

    public static BaseApplication getInstances() {
        return instances;
    }

    public static Context getAppsContext() {
        return appsContext;
    }

    private void setStrictPolicy() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void onCreate() {
        this.appsContext = this;
        this.instances = this;

        super.onCreate();
        setStrictPolicy();
    }

    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }*/

    public GlobalHelper getGlobalHelper() {
        if (this.globalHelper == null) {
            this.globalHelper = new GlobalHelper(this);
        }
        return this.globalHelper;
    }
}
