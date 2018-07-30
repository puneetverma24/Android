package in.codebucket.fragmentcommunication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Communicator{




    @Override
    public void respond(String data) {
        FragmentManager manager=getFragmentManager();
        FragmentB fragmentB=(FragmentB) manager.findFragmentById(R.id.fragment2);

  fragmentB.changeText(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
