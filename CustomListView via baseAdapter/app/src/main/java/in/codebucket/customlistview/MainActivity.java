package in.codebucket.customlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private final String[] data1={"a","b","c","d","e","f","g","h","i"};
    private final String[] data2={"1","2","3","4","5","6","6","7","8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.tool_list);
        listView.setAdapter(new CustomAdapter(this,data1,data2));

    }
}
