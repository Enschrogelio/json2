package com.ruby.x.json2.Controllers;

import android.content.Intent;

import com.ruby.x.json2.Helpers.GlobalHelper;
import com.ruby.x.json2.Views.MainActivity;
import com.ruby.x.json2.Views.SplashActivity;

public class SplashController {
    private SplashActivity splashActivity;
    private GlobalHelper globalHelper;

    public SplashController(SplashActivity activity) {
        this.splashActivity = activity;
        this.globalHelper = new GlobalHelper(activity);
    }

    public void doStartApplication() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    toActivity(MainActivity.class);
                }
            }
        };
        timerThread.start();
    }

    private void toActivity(Class<?> actCls) {
        Intent intent = new Intent(splashActivity, actCls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        splashActivity.startActivity(intent);
        splashActivity.finish();
    }
}
