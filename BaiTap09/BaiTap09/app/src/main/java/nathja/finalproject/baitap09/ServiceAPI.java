package nathja.finalproject.baitap09;

import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceAPI {
    public static final String BASE_URL = "http://app.iotstar.vn:8081/appfoods/";
    Gson gson = new GsonBuilder().setLenient().create();
    ServiceAPI serviceapi = new Retrofit.Builder().
    baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ServiceAPI.class);

    @Multipart
    @POST("updateimages.php")
/*    Call<UploadResponse> upload(
            @Part("username") RequestBody username,
            @Part MultipartBody.Part images
    );*/
    Call<ResponseBody> upload(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part images
    );


    @Multipart
    @POST("upload1.php")
    Call<Message> upload1(@Part(Const.MY_USERNAME) RequestBody username,
                          @Part MultipartBody.Part avatar);
}
