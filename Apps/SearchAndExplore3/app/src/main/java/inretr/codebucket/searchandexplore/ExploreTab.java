package inretr.codebucket.searchandexplore;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import inretr.codebucket.searchandexplore.model.AutoSearch;
import inretr.codebucket.searchandexplore.model.ReverseGeo;
import inretr.codebucket.searchandexplore.model.TextSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreTab  extends Fragment implements View.OnClickListener,LocationListener {



    private String[] result;
    String[] hello;
    private String[] icon;
    private String[] placeid;
    private String location;
    private LinearLayout bar;
    private LinearLayout bakery;
    private LinearLayout delivery;
    private LinearLayout takeaway;
    private LinearLayout cafe;
    private LinearLayout museum;
    private LinearLayout club;
    private LinearLayout restaurant;
    private LinearLayout spa;
    private LocationManager locationManager;

    private EditText edt;
    private ListView searchResult;
    private HorizontalScrollView hsv;
    private ImageView gps;


    public ExploreTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_explore_tab, container, false);
        init(view);




        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchResult.setVisibility(View.VISIBLE);
                hsv.setVisibility(View.GONE);



                getP(""+s);



            }
        });
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt.setText(result[position]);

                location=result[position];

               // Toast.makeText(ExploreTab.this, result[position], Toast.LENGTH_SHORT).show();
                searchResult.setVisibility(View.GONE);
                hsv.setVisibility(View.VISIBLE);

            }
        });





        return view;
    }




    private void getP(String keyword) {

        // Toast.makeText(MainActivity.this, "called" +keyword, Toast.LENGTH_SHORT).show();


        Call<AutoSearch> postList= ClientApi.getService().getPredictionList(keyword);


        postList.enqueue(new Callback<AutoSearch>() {
            @Override
            public void onResponse(Call<AutoSearch> call, Response<AutoSearch> response) {

                AutoSearch prediction = response.body();
                result=new String[prediction.getPredictions().size()];

                for(int i=0;i<prediction.getPredictions().size();i++) {

                    result[i]=response.body().getPredictions().get(i).getDescription();
                }
                // List<Search> heroList = response.body();


                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,result );

                searchResult.setAdapter(adapter);

                // Toast.makeText(MainActivity.this, "" + response.body().getPredictions(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<AutoSearch> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getPlaces(String category,String location) {


        String temp=category+" in "+location;


        Call<TextSearch> postList= ClientApi.getService().getTextResult(temp);


        postList.enqueue(new Callback<TextSearch>() {
            @Override
            public void onResponse(Call<TextSearch> call, Response<TextSearch> response) {

                TextSearch prediction = response.body();
                result=new String[prediction.getResults().size()];
                icon=new String[prediction.getResults().size()];
                placeid=new String[prediction.getResults().size()];



                for(int i=0;i<prediction.getResults().size();i++) {

                    result[i]=response.body().getResults().get(i).getName();
                    icon[i]=response.body().getResults().get(i).getIcon();
                    placeid[i]=response.body().getResults().get(i).getPlaceId();

                }
                // List<Search> heroList = response.body();




                Bundle b=new Bundle();
                b.putStringArray("data",result);


                Bundle a=new Bundle();
                a.putStringArray("icon",icon);
                //b.putStringArray("icon",icon);

                Bundle c=new Bundle();
                c.putStringArray("placeid",placeid);


                Intent i=new Intent(getActivity(),ResultList.class);

                i.putExtras(b);
                i.putExtras(a);
                i.putExtras(c);
                getActivity().startActivity(i);


            }

            @Override
            public void onFailure(Call<TextSearch> call, Throwable t) {
                Toast.makeText(getActivity(), "Check Your Net", Toast.LENGTH_SHORT).show();
            }
        });

    }







    @Override
    public void onClick(View v) {

                if(v==bar){getPlaces("bar",location);}
        if(v==bakery){getPlaces("bakery",location);}
        if(v==delivery){getPlaces("meal delivery",location);}
        if(v==takeaway){getPlaces("meal takeaway",location);}
        if(v==cafe){getPlaces("cafe",location);}
        if(v==museum){getPlaces("musem",location);}
        if(v==club){getPlaces("night club",location);}
        if(v==restaurant){getPlaces("restaurant",location);}
        if(v==spa){getPlaces("spa",location);}


        if(v==gps){

            if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            }

else
            {
                getLocation();
               getLastLocation();
            }


        }

    }


    private void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Toast.makeText(getActivity(), "Fetching Coordinates", Toast.LENGTH_LONG).show();
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d("TAG", provider);
            Log.d("TAG", location == null ? "NO LastLocation" : location.toString());

            //edt.setText(location.toString());

          //  edt.setVisibility(View.GONE);
         //   hsv.setVisibility(View.VISIBLE);


            String lat_lon = location.getLatitude() + "," + location.getLongitude();
            LatToPalce(lat_lon);


        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    private void LatToPalce(String lat_lon)
    {
        Call<ReverseGeo> postList= ClientApi.getService().getReverseGeo(lat_lon);

        Log.v("hellooo",lat_lon);

        postList.enqueue(new Callback<ReverseGeo>() {
            @Override
            public void onResponse(Call<ReverseGeo> call, Response<ReverseGeo> response) {

                ReverseGeo prediction = response.body();


              //String placee=response.body().getResults().get(0).getFormattedAddress();

                String placee=response.body().getResults().get(0).getAddressComponents().get(1).getShortName();
                edt.setText(placee);



               // Toast.makeText(getContext(), ""+placee, Toast.LENGTH_SHORT).show();

              //  Log.v("hellooo",""+place);

                String place=response.body().getResults().get(0).getAddressComponents().get(0).getShortName();



                /*
                result=new String[prediction.getPredictions().size()];
                 *

                for(int i=0;i<prediction.getPredictions().size();i++) {

                    result[i]=response.body().getPredictions().get(i).getDescription();
                }
                // List<Search> heroList = response.body();

*/
             //   ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,result );

               // searchResult.setAdapter(adapter);

                // Toast.makeText(MainActivity.this, "" + response.body().getPredictions(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ReverseGeo> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }



    @Override
    public void onLocationChanged(Location location) {
        //  locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

      //  Toast.makeText(this, "Lat"+location.getLatitude(), Toast.LENGTH_SHORT).show();
        Log.v("tag","hello"+location.getLatitude());

        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //     locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
            //           addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));

         //   Toast.makeText(this, "Latmnm,"+addresses.get(0), Toast.LENGTH_SHORT).show();


        }catch(Exception e)
        {
            Toast.makeText(getActivity(), "Enable To Fetch Gps data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }



    private void init(View view)
    {

        edt=view.findViewById(R.id.edt);
        searchResult=view.findViewById(R.id.search_result);
        hsv=view.findViewById(R.id.hsv);


        bar=view.findViewById(R.id.bar);
        bakery=view.findViewById(R.id.bakery);
        delivery=view.findViewById(R.id.delivery);
        takeaway=view.findViewById(R.id.takeaway);
        cafe=view.findViewById(R.id.cafe);
        museum=view.findViewById(R.id.museum);
        club=view.findViewById(R.id.club);
        restaurant=view.findViewById(R.id.restaurant);
        spa=view.findViewById(R.id.spa);

        gps=view.findViewById(R.id.gps);

        bar.setOnClickListener(this);
        bakery.setOnClickListener(this);
        delivery.setOnClickListener(this);
        takeaway.setOnClickListener(this);
        cafe.setOnClickListener(this);
        museum.setOnClickListener(this);
        club.setOnClickListener(this);
        restaurant.setOnClickListener(this);
        spa.setOnClickListener(this);
        gps.setOnClickListener(this);


    }


}
