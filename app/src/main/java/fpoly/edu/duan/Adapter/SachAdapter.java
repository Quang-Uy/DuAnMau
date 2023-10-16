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

import java.util.List;

import fpoly.edu.duan.DAO.LoaiSachDAO;
import fpoly.edu.duan.DTO.LoaiSach;
import fpoly.edu.duan.DTO.Sach;
import fpoly.edu.duan.Fragment.SachFragment;
import fpoly.edu.duan.R;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    SachFragment fragment;
    private List<Sach> list;
    TextView tv_MaSach, tv_TenSach, tv_GiaThue, tv_Loai;
    ImageView imgDel;

    public SachAdapter(@NonNull Context context, SachFragment fragment, List<Sach> list) {
        super(context, 0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sach, null);
        }

        final Sach item = list.get(position);
        if (item != null){
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(item.getMaLoai()));
            tv_MaSach = v.findViewById(R.id.tv_MaSach);
            tv_MaSach.setText("Mã sách: "+item.getMaSach());

            tv_TenSach = v.findViewById(R.id.tv_tenSach);
            tv_TenSach.setText("Tên sách: "+item.getTenSach());
            tv_GiaThue = v.findViewById(R.id.tv_GiaThue);
            tv_GiaThue.setText("Giá thuê: "+item.getGiaThue());
            tv_Loai = v.findViewById(R.id.tv_Loai);
            tv_Loai.setText("Loại sách: "+loaiSach.getTenLoai());

            imgDel = v.findViewById(R.id.imgDeleteSach);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xóa
                fragment.xoaSach(String.valueOf(item.getMaSach()));
            }
        });

        return v;
    }
}
