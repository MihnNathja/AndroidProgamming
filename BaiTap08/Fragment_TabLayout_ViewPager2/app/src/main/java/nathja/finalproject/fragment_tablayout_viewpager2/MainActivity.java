package nathja.finalproject.fragment_tablayout_viewpager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import nathja.finalproject.fragment_tablayout_viewpager2.R;

import nathja.finalproject.fragment_tablayout_viewpager2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPager2Adapter viewPager2Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //viewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //toolbar
        setSupportActionBar(binding.toolBar);

        FloatingActionButton fab = binding.fabAction;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
            }
        });

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Xác nhận"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lấy hàng"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager2Adapter = new ViewPager2Adapter (fragmentManager, getLifecycle());
        binding.viewPager2.setAdapter(viewPager2Adapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override

            public void onTabSelected (TabLayout. Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
                changeFabIcon(tab.getPosition());
            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected (TabLayout.Tab tab) {
            }
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab (binding.tabLayout.getTabAt(position));
            }
        });
    }
    private void changeFabIcon (final int index) {
        binding.fabAction.hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch(index){
                    case 0: binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_baseline_chat_24)); break;
                    case 1: binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_baseline_camera_alt_24));break;
                    case 2: binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_baseline_call_24));break;
                }
                binding.fabAction.show();
            }
        },  2000);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuSearch) {
            Toast.makeText(this, "You are selecting search", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuNewGroup) {
            Toast.makeText(this, "You are selecting more", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuBroadcast) {
            Toast.makeText(this, "You are selecting more", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuWeb) {
            Toast.makeText(this, "You are selecting more", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuMessage) {
            Toast.makeText(this, "You are selecting more", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuSetting) {
            Toast.makeText(this, "You are selecting Setting", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}