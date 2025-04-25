package nathja.finalproject.retrofit2exercise;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*public class RetrofitClient1 extends BaseClient {
    private static final String BASE_URL = "http://app.iotstar.vn:8081";
    private static APIService apiService;
    public static APIService getInstance() {
        if (apiService == null) {
            return createService (APIService.class, BASE_URL);
        }
        return apiService;
    }
    private void GetCategory() {
        //call Instance and execute Rquest thread
        apiService = RetrofitClient1.getInstance();
        apiService.getCategoriesAll().enqueue (new Callback<List<Category>>() {
            @Override
            public void onResponse (Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body(); //get array data
                    //initialize Adapter
                    category Adapter = new CategoryAdapter (RetrofitActivity.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager. HORIZONTAL,false);
                    rcCate.setLayoutManager (layoutManager);
                    rcCate.setAdapter (categoryAdapter);
                    category Adapter.notifyDataSetChanged();
                }else {
                    int statusCode = response.code();
                // handle request errors depending on status code
                }
            }
            @Override
            public void onFailure (Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}*/
