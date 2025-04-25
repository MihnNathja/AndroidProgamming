package nathja.finalproject.retrofit2exercise;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCate;
    //Declare Adapter

    CategoryAdapter categoryAdapter;

    APIService apiService;

    List<Category> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCategory();//load data for category
    }

    private void AnhXa() {
        //mapping
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
    }
    private void GetCategory() {
        //Call Interface in APIService
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse (Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()) {
                    categoryList = response.body(); //get array
                    //initialize Adapter
                    categoryAdapter = new CategoryAdapter (MainActivity.this,categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL,false);
                    rcCate.setLayoutManager (layoutManager);
                    rcCate.setAdapter (categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    int statusCode = response.code();
                // handle request errors depending on status code
                }
            }
            @Override
            public void onFailure (Call<List<Category>> call, Throwable t) {
                Log.d( "logg", t.getMessage());
            }
        });
    }
}