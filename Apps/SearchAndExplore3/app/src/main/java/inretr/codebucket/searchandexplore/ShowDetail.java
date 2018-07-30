package inretr.codebucket.searchandexplore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.GridView;
import android.widget.TextView;

public class ShowDetail extends AppCompatActivity   {



     //   private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        String namee=getIntent().getStringExtra("name");
        String address=getIntent().getStringExtra("address");

        String website=getIntent().getStringExtra("website");
        String rate=getIntent().getStringExtra("rate");
        //mDemoSlider = findViewById(R.id.slider);


        Bundle a=this.getIntent().getExtras();
        String[] photo_reference=a.getStringArray("photo_reference");


        TextView txtName=findViewById(R.id.name);
        TextView txtAddress=findViewById(R.id.address);
        TextView txtWebsite=findViewById(R.id.website);
        TextView txtrate=findViewById(R.id.rate);



        txtName.setText(namee);

        String add = "<b>Address: </b>" + address;
        txtAddress.setText(Html.fromHtml(add));


        String web = "<b>Website: </b>" + website;
        txtWebsite.setText(Html.fromHtml(web));

        String rat = "<b>Rating: </b>" + rate;
        txtrate.setText(Html.fromHtml(rat));



        final GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,photo_reference));


    }






}
