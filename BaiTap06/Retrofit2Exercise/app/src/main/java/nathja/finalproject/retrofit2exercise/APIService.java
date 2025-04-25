package nathja.finalproject.retrofit2exercise;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;
import retrofit2.Call;

public interface APIService {

    @GET("categories.php")
    Call<List<Category>> getCategoryAll();

}
