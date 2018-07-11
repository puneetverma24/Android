package in.codebucket.httpclientexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


          HttpClient httpClient = new DefaultHttpClient();
          HttpPost httpPost=new HttpPost("http://www.example.com/login");
          List<NameValuePair> nameValuePair=new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("email", "user"));
            nameValuePair.add(new BasicNameValuePair("Hi","Hello"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response=httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
}
