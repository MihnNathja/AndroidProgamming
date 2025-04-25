package nathja.finalproject.baitapview.gridview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import nathja.finalproject.baitapview.R;
import nathja.finalproject.baitapview.listview.MonHoc;
import nathja.finalproject.baitapview.listview.MonHocAdapter;

public class MainActivity3 extends AppCompatActivity {
    //khai báo
    GridView gridView;
    ArrayList<MonHoc> arrayList;
    MonHocAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ánh xạ
        AnhXa();
        //Tạo Adapter
        adapter = new MonHocAdapter(MainActivity3.this,
                R.layout.row_monhoc2,
                arrayList
        );
        //truyền dữ liệu từ adapter ra listview
        gridView.setAdapter(adapter);
    }
    private void AnhXa() {
        gridView = (GridView) findViewById(R.id.gridview2);
        //editText1 = (EditText) findViewById(R.id.editText1);
        //btnNhap = (Button) findViewById(R.id.btnNhap);
        //btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java1));
        arrayList.add(new MonHoc("C++","C++ 1",R.drawable.c_plus));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin 1",R.drawable.kotlin));
    }
}