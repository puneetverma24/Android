package in.codebucket.fragmentdemojava;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myFragment frag=new myFragment();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();

             transaction.add(R.id.my_layout,frag,"tag_name");
        transaction.commit();


    }
}
