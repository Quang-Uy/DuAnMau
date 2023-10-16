package fpoly.edu.duan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.edu.duan.DTO.LoaiSach;
import fpoly.edu.duan.Fragment.LoaiSachFragment;
import fpoly.edu.duan.R;

public class LoaiSachAdapter1 extends ArrayAdapter<LoaiSach> {

    private Context context;
    LoaiSachFragment fragment;
    private ArrayList<LoaiSach> list;
    TextView tv_MaLoaiSach, tv_TenLoaiSach;
    ImageView iv_Del;

    public LoaiSachAdapter1(@NonNull Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaisach, null);
        }
        final LoaiSach item = list.get(position);
        if (item != null) {
            tv_MaLoaiSach = v.findViewById(R.id.tv_MaLoaiSach);
            tv_MaLoaiSach.setText("Mã loại sách: "+item.getMaLoai());
            tv_TenLoaiSach = v.findViewById(R.id.tv_TenLoaiSach);
            tv_TenLoaiSach.setText("Thể loại: "+item.getTenLoai());
            iv_Del = v.findViewById(R.id.iv_delete);
        }
        iv_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phương thức xóa
                fragment.xoaLS(String.valueOf(item.getMaLoai()));
            }
        });
        return v;
    }
}
