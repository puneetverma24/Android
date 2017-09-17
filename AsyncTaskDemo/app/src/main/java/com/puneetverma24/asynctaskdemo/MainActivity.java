package com.puneetverma24.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=(TextView)findViewById(R.id.txt);

        new TestAsync().execute();
    }

    class TestAsync extends AsyncTask<Void, Integer, String>
    {
        String TAG = getClass().getSimpleName();

        protected void onPreExecute (){
            super.onPreExecute();
            Log.d(TAG + " PreExceute","On pre Exceute......");

            Toast.makeText(MainActivity.this, "On pre Exceute", Toast.LENGTH_SHORT).show();// can access UI
        }

        protected String doInBackground(Void...arg0) {
            Log.d(TAG + " DoINBackGround","On doInBackground...");


            for(int i=0; i<10; i++){
                Integer in = new Integer(i);
                publishProgress(i);
            }
            return "You are at PostExecute";
        }

        protected void onProgressUpdate(Integer...a){
            super.onProgressUpdate(a);

            Toast.makeText(MainActivity.this, "onProgressUpdate" +a[0], Toast.LENGTH_SHORT).show();// can access UI
            Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG + " onPostExecute", "" + result);
            Toast.makeText(MainActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();// can access UI
        }
    }

}


