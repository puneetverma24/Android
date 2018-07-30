package inretr.codebucket.searchandexplore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import inretr.codebucket.searchandexplore.model.DisplayDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private final String[] data;
    private final String[] icon;
    private final String[] placeid;

    private String address;
    private String name;
    private String phone;
    private String website;
    private String rate;
    private String vicinity;
    private String[] photo_reference;


    public DataAdapter(String[] data, String[] icon, String[] placeid) {
        this.data = data;
        this.icon = icon;
        this.placeid=placeid;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String title = data[position];
        holder.textView.setText(title);

        // holder.imageView.setImageDrawable();

        Picasso.get()
                .load(icon[position])
                .placeholder(R.drawable.placeholder).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView textView;

        MyViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      getAllDetail(v,placeid[getAdapterPosition()]);
                }
            });

            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.txt);
        }


    }


    private void getAllDetail(final View v,String temp) {

        Call<DisplayDetail> postList= ClientApi.getService().getDetail(temp);
        //placeid[id]

        postList.enqueue(new Callback<DisplayDetail>() {
            @Override
            public void onResponse(Call<DisplayDetail> call, Response<DisplayDetail> response) {

                DisplayDetail prediction = response.body();

                address=prediction.getResult().getFormattedAddress();
                name=prediction.getResult().getName();
                //phone;
                website=prediction.getResult().getWebsite();


              try {
                  rate = prediction.getResult().getRating().toString();
              }
              catch(Exception e){
                      rate="Rating Not Available";
            }

             //   Log.v("tag",""+prediction.getResult().getPhotos().get(0).getPhotoReference());

              try {
                  photo_reference = new String[prediction.getResult().getPhotos().size()];
                  for(int i=0;i<prediction.getResult().getPhotos().size();i++) {
                      photo_reference[i] = prediction.getResult().getPhotos().get(i).getPhotoReference();

                      Log.v("tag",""+ photo_reference[i]);

                  }
              }
               catch(Exception e){
                   photo_reference =new String[]{"1"};
                }



                //vicinity;

                Intent i=new Intent(v.getContext(),ShowDetail.class);
                i.putExtra("name",name);
                i.putExtra("address",address);
                i.putExtra("website",website);
                i.putExtra("rate",rate);

                Bundle a=new Bundle();
                a.putStringArray("photo_reference",photo_reference);
                i.putExtras(a);

                 v.getContext().startActivity(i);


            }

            @Override
            public void onFailure(Call<DisplayDetail> call, Throwable t) {
                    }
        });

    }

}
