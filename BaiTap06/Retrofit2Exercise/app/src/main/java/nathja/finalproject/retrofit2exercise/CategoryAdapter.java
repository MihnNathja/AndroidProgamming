package nathja.finalproject.retrofit2exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> array;

    public CategoryAdapter (Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView images;
        public TextView tenSp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.image_cate);
            tenSp = (TextView) itemView.findViewById(R.id.tvNameCategory);
            //catch event for item holder in MyViewHolder
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //process when clicking on Item on category
                    Toast.makeText(context, "You have selected a category" + tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onBindViewHolder (@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.tenSp.setText(category.getName());
        //load him with Glide
        Glide.with(context).load(category.getImages()).into (holder.images);
    }

    @Override
    public int getItemCount() { return array == null ? 0 : array.size(); }
}
