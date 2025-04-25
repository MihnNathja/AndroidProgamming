package nathja.finalproject.indicator_and_search_in_recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcIcon;
    private List<IconModel> arrayList;
    private IconAdapter iconAdapter;
    private SearchView searchView;
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

        rcIcon = findViewById(R.id.rcIcon);
        arrayList = new ArrayList<>();
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill1"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill2"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill3"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill4"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));
        arrayList.add(new IconModel(R.drawable.ic_baseline_chat_24,"Chill Chill"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL,false);
        rcIcon.setLayoutManager(gridLayoutManager);
        iconAdapter = new IconAdapter(getApplicationContext(), arrayList);
        rcIcon.setAdapter(iconAdapter);
        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration(this));

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange (String newText) {
                filterListener(newText);
                return true;
            }
        });
    }
    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();
        for (IconModel iconModel:arrayList){
            if(iconModel.getDesc().toLowerCase().contains(text.toLowerCase())){
                list.add(iconModel);
            }
        }
        if(list.isEmpty()){
            Toast.makeText( this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            iconAdapter.setListenerList(list);
        }
    }
    public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
        private final int indicatorHeight = 16; // Chiều cao của chỉ báo
        private final float indicatorStrokeWidth = 8f; // Độ dày của đường chỉ báo
        private final float indicatorItemPadding = 16f; // Khoảng cách giữa các chỉ báo
        private final Paint inactivePaint = new Paint();
        private final Paint activePaint = new Paint();

        public LinePagerIndicatorDecoration(Context context) {
            // Màu chỉ báo chưa được chọn
            inactivePaint.setStrokeWidth(indicatorStrokeWidth);
            inactivePaint.setColor(context.getResources().getColor(android.R.color.darker_gray));
            inactivePaint.setStyle(Paint.Style.STROKE);
            inactivePaint.setAntiAlias(true);

            // Màu chỉ báo được chọn
            activePaint.setStrokeWidth(indicatorStrokeWidth);
            activePaint.setColor(context.getResources().getColor(android.R.color.holo_blue_light));
            activePaint.setStyle(Paint.Style.FILL);
            activePaint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(canvas, parent, state);

            int itemCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();
            if (itemCount <= 0) return;

            float totalWidth = indicatorItemPadding * (itemCount/2 - 1);
            float indicatorStartX = (parent.getWidth() - totalWidth) / 2f;
            float indicatorPosY = parent.getHeight() - indicatorHeight;

            // Vẽ các chỉ báo chưa được chọn
            drawInactiveIndicators(canvas, indicatorStartX, indicatorPosY, itemCount);

            // Lấy vị trí của mục hiện tại
            int activePosition = ((GridLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) return;

            // Vẽ chỉ báo được chọn
            drawActiveIndicator(canvas, indicatorStartX, indicatorPosY, activePosition);
        }

        private void drawInactiveIndicators(Canvas canvas, float startX, float posY, int itemCount) {
            float itemWidth = indicatorItemPadding;

            for (int i = 0; i < itemCount/2; i++) {
                float x = startX + (itemWidth * i);
                canvas.drawCircle(x, posY, indicatorStrokeWidth, inactivePaint);
            }
        }

        private void drawActiveIndicator(Canvas canvas, float startX, float posY, int highlightPosition) {
            float itemWidth = indicatorItemPadding;
            float highlightX = startX + (itemWidth * highlightPosition);

            canvas.drawCircle(highlightX, posY, indicatorStrokeWidth, activePaint);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.set(0, 0, 0, indicatorHeight); // Đặt khoảng cách để chỉ báo không bị che
        }
    }
}