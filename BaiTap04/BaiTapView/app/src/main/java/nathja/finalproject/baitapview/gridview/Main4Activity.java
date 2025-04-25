package nathja.finalproject.baitapview.gridview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import nathja.finalproject.baitapview.R;
import nathja.finalproject.baitapview.listview.MainActivity;

public class Main4Activity extends AppCompatActivity {
    //khai báo
    GridView gridView;
    ArrayList<String> arrayList;
    EditText editText1;
    Button btnNhap;
    Button btnCapnhat;
    Button btnXoa;
    int vitri = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xạ
        gridView = (GridView) findViewById(R.id.gridview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        //Tạo ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                Main4Activity.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        //truyền dữ liệu từ adapter ra gridview
        gridView.setAdapter(adapter);

        //bắt sự kiện click nhanh trên từng dòng của Gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên GridView -> i ban đầu =0
                Toast.makeText(Main4Activity.this,"" + arrayList.get(i),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //bắt sự kiện click giữ trên từng dòng của Gridview
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                //i: trả về vị trí click chuột trên GridView -> i ban đầu =0
                Toast.makeText(Main4Activity.this,
                                "Bạn đang nhấn giữ "+ i + "-" + arrayList.get(i),
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
        //bắt sự kiện trên từng dòng của Gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        //Xóa item
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