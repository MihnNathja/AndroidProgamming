package nathja.finalproject.retrofit2exercise;


import retrofit2. Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    // API path
                    .baseUrl("http://app.iotstar.vn:8081/appfoods/")
                    .addConverterFactory (GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}