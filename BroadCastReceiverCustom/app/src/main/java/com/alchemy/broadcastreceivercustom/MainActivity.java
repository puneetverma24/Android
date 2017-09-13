package com.alchemy.broadcastreceivercustom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                broadcastIntent();
            }
        });





    }

    public void broadcastIntent(){
        Intent intent = new Intent();
        intent.setAction("com.example.CUSTOM_INTENT"); sendBroadcast(intent);
    }

}
