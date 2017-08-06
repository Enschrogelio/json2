package com.ruby.x.json2.Helpers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ruby.x.json2.DataAccess.ServerDataAccess;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.R;
import com.ruby.x.json2.Views.SplashActivity;

public class GlobalHelper {
    public Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private UIHelper uiHelper;
    private UserHelper userHelper;
    private ServerDataAccess serverDataAccess;
    private ImageHelper imageHelper;
    private DataTask dataTask;

    public GlobalHelper(Context context) {
        this.context = context;
    }

    public SharedPreferencesHelper getSharedPreferencesHelper() {
        if (this.sharedPreferencesHelper == null) {
            this.sharedPreferencesHelper = new SharedPreferencesHelper(this.context);
        }
        return this.sharedPreferencesHelper;
    }

    public String getDevicesID() {
        return Settings.Secure.getString(this.context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(c.getTime());
    }

    public UIHelper getUIHelper() {
        if (this.uiHelper == null) {
            this.uiHelper = new UIHelper(this.context);
        }
        return uiHelper;
    }

    public UserHelper getUserHelper() {
        if (this.userHelper == null) {
            this.userHelper = new UserHelper(this.context);
        }
        return this.userHelper;
    }

    public ImageHelper getImageHelper() {
        if (this.imageHelper == null) {
            this.imageHelper = new ImageHelper(this.context);
        }
        return this.imageHelper;
    }

    public ServerDataAccess getServerDataAccess() {
        if (this.serverDataAccess == null) {
            this.serverDataAccess = new ServerDataAccess(this.context);
        }
        return this.serverDataAccess;
    }

    public DataTask getDataTask() {
        if (dataTask == null) {
            dataTask = new DataTask();
        }
        return dataTask;
    }

    public static final String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Notification showNotification(String str, Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
        builder.setContentIntent(pendIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker(str);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(false);
        builder.setContentTitle(context.getResources().getString(R.string.app_name));
        builder.setContentText(str);
        Notification notification = builder.build();
        return notification;
    }

    public String getVersionNumber() {
        String version = "0.0";
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public String getRandomNumber() {
        return String.valueOf(System.currentTimeMillis());
    }

    public void writeTextFile(String text) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
            Date date = new Date();
            File root = new File(Environment.getExternalStorageDirectory(), DataConstant.FOLDER_SAVE);
            if (!root.exists()) {
                android.util.Log.e("WriteText", "Root Not Exists");
                if (!root.mkdir()) {
                    android.util.Log.e("WriteText", "Failed to create direcktory");
                    return;
                }
            }

            File fileLog = new File(root, "log-" + format.format(date) + ".txt");
            if (!fileLog.exists()) {
                android.util.Log.e("WriteText", "Can't found file Log");
                if (!fileLog.createNewFile()) {
                    android.util.Log.e("WriteText", "Can't create file Log");
                    return;
                }
            }

            BufferedWriter buf = new BufferedWriter(new FileWriter(fileLog, true));
            final String currentDateTimeString = timeformat.format(new Date());
            buf.append(currentDateTimeString + " : " + text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        android.util.Log.i("WriteText", "write : " + text);
    }

    public boolean checkFileDirExternal() {
        File folder = new File(Environment.getExternalStorageDirectory(), DataConstant.FOLDER_SAVE);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
            return success;
        } else {
            return true;
        }
    }

    public File createFileFromBitmap(Bitmap bitmap, String file_name) throws FileNotFoundException, IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File destination = null;
        if (checkFileDirExternal()) {
            destination = new File(Environment.getExternalStorageDirectory(), DataConstant.FOLDER_SAVE +
                    DataConstant.DEFAULT_FILENAME + file_name + ".png");
            FileOutputStream fo;
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        return destination;
    }
}
