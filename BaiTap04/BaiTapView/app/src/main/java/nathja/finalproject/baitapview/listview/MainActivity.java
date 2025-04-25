package nathja.finalproject.baitapview.listview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import nathja.finalproject.baitapview.R;

public class MainActivity extends AppCompatActivity {
    //Khai báo
    Button btnCapnhat;
    EditText editText1;
    Button btnNhap;
    Button btnXoa;
    int vitri = -1;

    //khai báo
    ListView listView;
    ArrayList<MonHoc> arrayList;
    MonHocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //khai báo
        ListView listView;
        ArrayList<String> arrayList;

        //ánh xạ
        listView = (ListView) findViewById(R.id.listview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        //Tạo ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );
        //truyền dữ liệu từ adapter ra listview
        listView.setAdapter(adapter);

        //bắt sự kiện click nhanh trên từng dòng của Listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên ListView -> i ban đầu =0
                Toast.makeText(MainActivity.this,
                                ""+ arrayList.get(i),
                                Toast.LENGTH_SHORT).show();
            }
        });

        //bắt sự kiện click giữ trên từng dòng của Listview
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên ListView -> i ban đầu =0
                Toast.makeText(MainActivity.this,
                        "Bạn đang nhấn giữ "+ i + "-" + arrayList.get(i) ,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        editText1 = (EditText) findViewById(R.id.editText1);
        btnNhap = (Button) findViewById(R.id.btnNhap);

        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                arrayList.add(name);
                adapter.notifyDataSetChanged();
            }
        });



        //ánh xạ
        btnCapnhat = (Button) findViewById(R.id.btnCapnhat);

        //bắt sự kiện trên từng dòng của Listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lấy nội dung đua lên edittext
                editText1.setText(arrayList.get(i));
                vitri = i;
            }
        });

        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.set(vitri, editText1.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        btnXoa = (Button) findViewById(R.id.btnXoa);
        //Xóa item
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitri != -1) {
                    arrayList.remove(vitri);
                    adapter.notifyDataSetChanged();
                    vitri = -1;
                }
            }
        });
    }
}