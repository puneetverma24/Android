package inretr.codebucket.searchandexplore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ResultList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);



        Bundle b=this.getIntent().getExtras();
        String[] data=b.getStringArray("data");

        Bundle a=this.getIntent().getExtras();
        String[] icon=a.getStringArray("icon");


        Bundle c=this.getIntent().getExtras();
        String[] placeid=c.getStringArray("placeid");


        RecyclerView recyclerView= findViewById(R.id.programmingList);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DataAdapter(data,icon,placeid));




    }








}
