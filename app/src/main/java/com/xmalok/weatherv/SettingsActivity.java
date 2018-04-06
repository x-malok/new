package com.xmalok.weatherv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {
final  String LOG_TAG= "mylog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"Settings Activity onCreate");
        setContentView(R.layout.activity_settings);
    }
}
