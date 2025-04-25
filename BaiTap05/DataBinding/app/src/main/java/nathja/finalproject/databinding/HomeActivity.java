package nathja.finalproject.databinding;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import nathja.finalproject.databinding.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements ListUserAdapter.OnItemClickListener {
    public ObservableField<String> title = new ObservableField<>();
    private ListUserAdapter listUserAdapter;
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        title.set("DataBinding Example for RecycleView");
        binding.setHome(this);
        setData();
        binding.rcView.setLayoutManager(new LinearLayoutManager(this));
        binding.rcView.setAdapter(listUserAdapter);
        listUserAdapter.setOnItemClickListener(this);
    }

    private void setData() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName("Minh" + i);
            user.setLastName("Nhật" + i);
            userList.add(user);
        }
        listUserAdapter = new ListUserAdapter(userList);
    }
    @Override
    public void itemClick(User user) {
        Toast.makeText(this, "bạn vừa click", Toast.LENGTH_SHORT).show();
    }
}