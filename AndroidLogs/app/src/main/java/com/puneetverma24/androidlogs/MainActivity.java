package com.puneetverma24.androidlogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
String tag="logs";

        Log.v(tag,"verbose");
        Log.d(tag,"Debug");
        Log.i(tag,"info");
        Log.w(tag,"warn");
        Log.e(tag,"error");


    }
}
