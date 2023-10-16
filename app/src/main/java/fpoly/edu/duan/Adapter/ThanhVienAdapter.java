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

import fpoly.edu.duan.DTO.ThanhVien;
import fpoly.edu.duan.Fragment.ThanhVienFragment;
import fpoly.edu.duan.R;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    ThanhVienFragment fragment;
    private ArrayList<ThanhVien> list;

    TextView tvMaTV, tvTenTV, tvNamSinh;
    ImageView imgDel;


    public ThanhVienAdapter(@NonNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> list) {
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
            v = inflater.inflate(R.layout.item_thannhvien, null);
        }
        final ThanhVien item = list.get(position);
        if (item != null) {
            tvMaTV = v.findViewById(R.id.tv_MaTV);
            tvMaTV.setText("Mã thành viên: "+item.getMaTV());
            tvTenTV = v.findViewById(R.id.tv_TenTV);
            tvTenTV.setText("Tên thành viên: "+item.getHoTen());
            tvNamSinh = v.findViewById(R.id.tv_NamSinh);
            tvNamSinh.setText("Năm sinh: "+item.getNamSinh());
            imgDel = v.findViewById(R.id.imgDeleteTV);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phương thức xóa
                fragment.xoaTV(String.valueOf(item.getMaTV()));
            }
        });
        return v;
    }
}
