package in.codebucket.servicepractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, ""+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        String msg = "Android : ";


            Log.d(msg, "The onCreate() event");
        }

    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));

        //getApplicationContext
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }
}