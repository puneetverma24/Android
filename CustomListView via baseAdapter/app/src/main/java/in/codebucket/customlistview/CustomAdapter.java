package in.codebucket.customlistview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

class CustomAdapter extends BaseAdapter{

    private final String [] data1;
    private final Context context;
    private final String[] data2;

    private static LayoutInflater inflater=null;



    public CustomAdapter(Context context, String[] data1, String[] data2) {
        // TODO Auto-generated constructor stub

        this.context=context;
        this.data1=data1;
        this.data2=data2;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data1.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        TextView tv,tv2;

        rowView = inflater.inflate(R.layout.row, null);
        tv= rowView.findViewById(R.id.textView1);
        tv2= rowView.findViewById(R.id.textView2);

        tv.setText(data1[position]);
        tv2.setText(data2[position]);

        //holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        //holder.img.setImageResource(imageId[position]);


        rowView.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();

            }
        });

        return rowView;
    }
}
