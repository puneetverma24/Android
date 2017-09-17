package com.puneetverma24.threadhandlerlooper;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int count=0;
    Handler handler;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.btn);
        txt=(TextView)findViewById(R.id.txt);


        handler=new Handler(getApplicationContext().getMainLooper());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        while(true) {
                            try {
                                Thread.sleep(1000);
                                count++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.v("check", "" + count);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt.setText(""+count);
                                }
                            });
                        }
                    }
                }).start();

            }
        });




    }
}
