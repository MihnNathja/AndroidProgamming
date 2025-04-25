package nathja.finalproject.baitapview.listview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import nathja.finalproject.baitapview.R;

public class MainActivity2 extends AppCompatActivity {
    //Khai báo
    Button btnCapnhat;
    EditText editText1;
    Button btnNhap;
    int vitri = -1;

    //khai báo
    ListView listView;
    //khai báo
    ArrayList<MonHoc> arrayList;
    MonHocAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xạ
        AnhXa();
        //Tạo Adapter
        adapter = new MonHocAdapter(MainActivity2.this,
                R.layout.row_monhoc,
                arrayList
        );
        //truyền dữ liệu từ adapter ra listview
        listView.setAdapter(adapter);

    }
    private void AnhXa() {
        listView = (ListView) findViewById(R.id.listview1);
        //editText1 = (EditText) findViewById(R.id.editText1);
        //btnNhap = (Button) findViewById(R.id.btnNhap);
        //btnCapnhat = (Button) findViewById(R.id.btnCapnhat);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java1));
        arrayList.add(new MonHoc("C++","C++ 1",R.drawable.c_plus));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin 1",R.drawable.kotlin));
    }
}