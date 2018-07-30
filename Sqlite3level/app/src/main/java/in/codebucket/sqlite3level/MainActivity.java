package in.codebucket.sqlite3level;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

 DbAdapter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adpter=new DbAdapter(this);

        Toast.makeText(this, "Inserting Data", Toast.LENGTH_LONG).show();
        insertCaller("Codebucket",10);
        insertCaller("Ashok",77);

        displayCaller();

        Toast.makeText(this, "Calling data roll=10", Toast.LENGTH_LONG).show();

        getDataCaller(10);

        Toast.makeText(this, "Updating Data for roll=77", Toast.LENGTH_LONG).show();

        updateCaller(77,"Android");
        Toast.makeText(this, "Deleting data for roll=77", Toast.LENGTH_LONG).show();

        deleteCaller(77);

        Toast.makeText(this, "Deleting All data", Toast.LENGTH_LONG).show();
        deleteAllCaller();

    }


    public void insertCaller(String name, int roll)
    {
        long id= adpter.insertRow(name,roll);
      //  Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
    }


    public void displayCaller()
    {

        Toast.makeText(this, ""+adpter.display(), Toast.LENGTH_SHORT).show();
    }

    public void getDataCaller(int roll)
    {


        Toast.makeText(this, ""+ adpter.getData(roll), Toast.LENGTH_SHORT).show();

    }

    public void updateCaller(int roll,String name)
    {



      //Toast.makeText(this, ""+adpter.updateRow(roll,name), Toast.LENGTH_SHORT).show();
        displayCaller();

    }


    public void deleteCaller(int roll)
    {
        adpter.deleteRow(roll);
        displayCaller();

    }
    public void deleteAllCaller()
    {
        adpter.deleteAll();
        displayCaller();

    }


}

