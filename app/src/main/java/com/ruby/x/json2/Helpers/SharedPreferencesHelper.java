package com.ruby.x.json2.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import com.ruby.x.json2.Models.DataConstant;

public class SharedPreferencesHelper extends GlobalHelper {

    SharedPreferences preferences;
    DataConstant constant;

    public SharedPreferencesHelper(Context context) {
        super(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public void setPrefIsLogin(boolean isLogin) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(constant.PREF_IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean getPrefIsLogin() {
        return preferences.getBoolean(constant.PREF_IS_LOGIN, false);
    }

    public void setPrefAuthToken(String auth_token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(constant.PREF_AUTH_TOKEN, auth_token);
        editor.commit();
    }

    public String getPrefAuthToken() {
        return preferences.getString(constant.PREF_AUTH_TOKEN, "");
    }


}
