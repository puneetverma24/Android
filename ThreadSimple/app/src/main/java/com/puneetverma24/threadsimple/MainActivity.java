package com.puneetverma24.threadsimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("check",""+Thread.currentThread().getName());
        Button btn=(Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("check",""+Thread.currentThread().getName());
                       // Toast.makeText(MainActivity.this, ""+Thread.currentThread().getId(), Toast.LENGTH_SHORT).show();
                    }
                }).start();

            }
        });





    }





}



