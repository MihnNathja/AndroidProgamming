package nathja.finalproject.thread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> rowArrayList = new ArrayList<>();
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        populateData();
        initAdapter();
        initScrollListener();
    }
    private void populateData(){
        int i = 0;
        while (i < 50){
            rowArrayList.add("Item " + i);
            i++;
        }
    }
    private void initAdapter(){
        recyclerViewAdapter = new RecyclerViewAdapter(rowArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    private void initScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged (@NonNull RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }
    private void loadMore(){
        rowArrayList.add(null);
        recyclerViewAdapter.notifyItemInserted(rowArrayList.size() - 1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rowArrayList.remove(rowArrayList.size() - 1);
                int scrollPosition = rowArrayList.size();
                recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10 ;

                while (currentSize - 1 < nextLimit){
                    rowArrayList.add("Item " + currentSize);
                    currentSize++;
                }
                Toast.makeText(getApplicationContext(), "Loaded more items", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
                }
        },1000);
    }
}