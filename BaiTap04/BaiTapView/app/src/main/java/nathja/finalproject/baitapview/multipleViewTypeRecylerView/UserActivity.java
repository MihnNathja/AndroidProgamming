package nathja.finalproject.baitapview.multipleViewTypeRecylerView;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nathja.finalproject.baitapview.R;


public class UserActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView rvMultipleViewType;

    private List<Object> mData;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        rvMultipleViewType = (RecyclerView) findViewById(R.id.rv_multipe_view_type);
        mData = new ArrayList<>();
        mData.add(new UserModel ( "Nguyen Van Nghia", "Quan 11"));
        mData.add(R.drawable.kotlin);
        mData.add("Text 0");
        mData.add("Text 1");
        mData.add(new UserModel ( "Nguyen Hoang Minh",  "Quan 3"));
        mData.add("Text 2");
        mData.add(R.drawable.kotlin);
        mData.add(R.drawable.kotlin);
        mData.add(new UserModel ( "Pham Nguyen Tam Phu",  "Quan 10"));
        mData.add("Text 3");
        mData.add("Text 4");
        mData.add(new UserModel( "Tran Van Phuc", "Quan 1"));
        mData.add(R.drawable.kotlin);
        CustomAdapter adapter = new CustomAdapter (this, mData);

        rvMultipleViewType.setAdapter(adapter);

        rvMultipleViewType.setLayoutManager(new LinearLayoutManager(this));

    }
}