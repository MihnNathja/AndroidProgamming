package nathja.finalproject.sqliteexercise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // khai báo biến toàn cục
    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        // gọi hàm databaseSQLite
        InitDatabaseSQLite();
        //createDatabaseSQLite();
        databaseSQLite();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //bắt sự kiện cho menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_notes);

        //ánh xạ trang dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonThem);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        //bắt sự kiện cho nút thêm và hủy
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if (name.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '"+ name +"')" );
                    Toast.makeText(MainActivity.this,"Đã thêm Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite(); // gọi hàm load lại dữ liệu
                }
            }
        });

        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void DialogUpdate(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_notes);

        // Ánh xạ các thành phần
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonCapNhat);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);
        editText.setText(name);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString().trim();
                databaseHandler.QueryData("UPDATE Notes SET NameNotes ='"+ name+"' WHERE Id = '"+ id+"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật Notes thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            }
        });

        // Nút hủy
        buttonHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    //delete dialog function

    public void DialogDelete (String name, final int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setMessage("Bạn có muốn xóa notes "+ name + " này không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE FROM Notes WHERE Id = "+ id);
                Toast.makeText(MainActivity.this, "Notes " + name + " deleted successfully", Toast.LENGTH_SHORT).show();
                databaseSQLite();//call the function to reload data
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


    private void createDatabaseSQLite() {
        // thêm dữ liệu vào bảng
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 2')");
    }

    private void InitDatabaseSQLite() {
        // khởi tạo database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);

        // tạo bảng Notes
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void databaseSQLite() {
        arrayList.clear(); // xoá để tránh trường hợp bị chèn thêm một lần dữ liệu cũ
        // Lấy dữ liệu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
        }
        adapter.notifyDataSetChanged();
    }
}
