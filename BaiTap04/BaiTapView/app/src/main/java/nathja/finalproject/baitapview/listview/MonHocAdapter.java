package nathja.finalproject.baitapview.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import nathja.finalproject.baitapview.R;
public class MonHocAdapter extends BaseAdapter {
    //khai báo
    private Context context; // activity kết nối
    private int layout; // bố cục thể hiện
    private List<MonHoc> monHocList; // dữ liệu truyền tải

    //tạo Constructors
    public MonHocAdapter(Context context, int
            layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }
    //trả về số dòng
    @Override
    public int getCount() {
        return monHocList.size(); //lấy kích thước monhoclist
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //khởi tạo viewholder
        ViewHolder viewHolder;
        //lấy context
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //gọi view chứa layout
            view = inflater.inflate(layout,null);
            //ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView) view.findViewById(R.id.textName);
            viewHolder.textDesc = (TextView) view.findViewById(R.id.textDesc);
            viewHolder.imagePic = (ImageView) view.findViewById(R.id.imagePic);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }//gán giá trị
        MonHoc monHoc = monHocList.get(i);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());
        //trả về view
        return view;
    }
    private class ViewHolder{
        // một lớp con dùng để chứa các thông tin xuất hiện trong một view được ánh xạ
        TextView textName,textDesc;
        ImageView imagePic;
    }
}
