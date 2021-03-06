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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


public class SearchTab  extends Fragment implements View.OnClickListener ,LocationListener {

    private String[] result;
    private String[] icon;
    private String[] placeid;
    private String location;
   // private LinearLayout bar,bakery,delivery,takeaway,cafe,museum,club,restaurant,spa;
    private LocationManager locationManager;
    private Button btn_search;
    private EditText edt;
    private EditText edtkeyword;
    private ListView searchResult;

    private ImageView gps;


    public SearchTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_search_tab, container, false);

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
                edtkeyword.setVisibility(View.GONE);
                btn_search.setVisibility(View.GONE);
                getP(""+s);

            }
        });
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt.setText(result[position]);

                location=result[position];
                searchResult.setVisibility(View.GONE);
                edtkeyword.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.VISIBLE);

            }
        });





        return view;
    }
    private void init(View view)
    {

        edt=view.findViewById(R.id.search_tab_search);
        edtkeyword=view.findViewById(R.id.search_tab_keyword);
        searchResult=view.findViewById(R.id.search_tab_list_result);
        gps=view.findViewById(R.id.search_tab_gps) ;

        btn_search=view.findViewById(R.id.search_btn);
           btn_search.setOnClickListener(this);


           gps.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {



                   if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                       ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                   }

                   else
                   {
                       try {


                       getLocation();
                       getLastLocation();}
                       catch (Exception e){};

                   }









               }
           });


    }



    private void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Toast toast=Toast.makeText(getActivity(), "Fetching Coordinates", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
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


            if(location==null)
            {

                Toast toast=Toast.makeText(getActivity(), "Enable To Fetch Gps data", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }




            LatToPalce(location.getLatitude() + "," + location.getLongitude());


        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {

        String a=edtkeyword.getText().toString();

        if(a.equals(""))
        {
            Toast.makeText(getActivity(), "Please Input Search Keyword", Toast.LENGTH_SHORT).show();
        }
        else
        getPlaces(a,location);






    }


    private void getP(String keyword) {



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

    private void LatToPalce(String lat_lon)
    {
        Call<ReverseGeo> postList= ClientApi.getService().getReverseGeo(lat_lon);



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

            Toast toast=Toast.makeText(getActivity(), "Enable To Fetch Gps data", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
          Toast toast=Toast.makeText(getActivity(), "Please Enable GPS", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }



}


