package in.codebucket.customlistviewarrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    String[] data1={"a","b","c","d","e"};
    String[] data2={"aa","bb","cc","dd","ee"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.lst);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.row,R.id.txt1,data1);
        listView.setAdapter(adapter);


    }
}
