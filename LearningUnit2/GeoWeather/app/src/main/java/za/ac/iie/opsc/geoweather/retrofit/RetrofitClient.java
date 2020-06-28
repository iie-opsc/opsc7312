package za.ac.iie.opsc.geoweather.retrofit;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private RetrofitClient() {}

    private static Retrofit retrofit;

    public static Retrofit getRetrofit()
    {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dataservice.accuweather.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return retrofit;
    }
}
