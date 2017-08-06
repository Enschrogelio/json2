package com.ruby.x.json2.Views;

import android.os.Bundle;
import android.widget.TextView;

import com.ruby.x.json2.BaseActivity;
import com.ruby.x.json2.Controllers.SplashController;
import com.ruby.x.json2.R;

public class SplashActivity extends BaseActivity {

    private SplashController splashController;
    private TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.splashController = new SplashController(this);
        initUI();
        initValue();
        initAttribute();
    }

    private void initUI() {
        setFullScreen();
        setNoActionBar();
        this.setContentView(R.layout.activity_splash);
        //txtVersion = (TextView) findViewById(R.id.txtVersion);
    }

    private void initValue() {
        //txtVersion.setText(getGlobalHelper().getVersionNumber());
        splashController.doStartApplication();

        //final AlertDialog dialog = getGlobalHelper().getUIHelper().getSpotsDialog(this);
        //dialog.show();
    }

    private void initAttribute() {

    }
}
