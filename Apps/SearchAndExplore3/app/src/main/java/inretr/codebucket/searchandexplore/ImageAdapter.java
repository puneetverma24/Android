package inretr.codebucket.searchandexplore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    //int imageTotal = 7;

    private final String[] photo_reference;


    public ImageAdapter(Context c,String[] photo_reference) {
        mContext = c;
        this.photo_reference=photo_reference;

    }

    public int getCount() {
        return photo_reference.length;
    }

    @Override
    public String getItem(int position) {
        return photo_reference[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(480, 480));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        String url="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+getItem(position)+"&key=AIzaSyCrAkJhbJRPxsT12nnq6EGV9dLzSSI3bLI";


        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop().into(imageView);
        return imageView;
    }
}