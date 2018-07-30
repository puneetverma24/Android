package inretr.codebucket.searchandexplore;


import inretr.codebucket.searchandexplore.model.AutoSearch;
import inretr.codebucket.searchandexplore.model.DisplayDetail;
import inretr.codebucket.searchandexplore.model.ReverseGeo;
import inretr.codebucket.searchandexplore.model.TextSearch;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ClientApi {
   //Dummy Api
    private static final String key="Dummy Api";
    private static final String url="https://maps.googleapis.com/maps/api/place/";



    private static SearchInterface searchInterface=null;

        public static SearchInterface getService()
        {
            if(searchInterface==null)
            {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                        .build();

                searchInterface=retrofit.create(SearchInterface.class);
            }
            return searchInterface;

        }


public interface SearchInterface
{

    @GET("autocomplete/json?key="+key)
    Call<AutoSearch> getPredictionList(@Query("input") String keyword);



    @GET("textsearch/json?key="+key)
    Call<TextSearch> getTextResult(@Query("query") String temp);


    @GET("details/json?fields=name,rating,formatted_phone_number,opening_hours,website,photo,vicinity,formatted_address&key="+key)
    Call<DisplayDetail> getDetail(@Query("placeid") String placeid);


   @GET("http://maps.googleapis.com/maps/api/geocode/json?sensor=true")
  Call<ReverseGeo> getReverseGeo(@Query("latlng") String lat_lon);



}

}
