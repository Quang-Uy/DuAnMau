package fpoly.edu.duan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.edu.duan.DTO.LoaiSach;
import fpoly.edu.duan.R;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    Context context;
    ArrayList<LoaiSach> list;
    TextView tv_MaLoaiSach, tv_TenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull Context context, ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loaisach_item_spinner, null);
        }

        final LoaiSach item = list.get(position);
        if (item != null){
            tv_MaLoaiSach = v.findViewById(R.id.tv_MaLoaiSachSpn);
            tv_MaLoaiSach.setText(item.getMaLoai() + ". ");

            tv_TenLoaiSach = v.findViewById(R.id.tv_TenLoaiSachSpn);
            tv_TenLoaiSach.setText(item.getTenLoai());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loaisach_item_spinner, null);
        }

        final LoaiSach item = list.get(position);
        if (item != null){
            tv_MaLoaiSach = v.findViewById(R.id.tv_MaLoaiSachSpn);
            tv_MaLoaiSach.setText(item.getMaLoai() + ". ");

            tv_TenLoaiSach = v.findViewById(R.id.tv_TenLoaiSachSpn);
            tv_TenLoaiSach.setText(item.getTenLoai());
        }

        return v;
    }
}
