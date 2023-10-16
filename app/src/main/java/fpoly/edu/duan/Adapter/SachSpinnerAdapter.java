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

import fpoly.edu.duan.DTO.Sach;
import fpoly.edu.duan.R;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private ArrayList<Sach> list;
    TextView tv_maSach, tv_tenSach;

    public SachSpinnerAdapter(@NonNull Context context, ArrayList<Sach> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sach_spinner, null);
        }
        final Sach item = list.get(position);
        if (item!=null){
            tv_maSach = v.findViewById(R.id.tv_MaSachSP);
            tv_maSach.setText(item.getMaSach() + ". ");
            tv_tenSach = v.findViewById(R.id.tv_TenSachSP);
            tv_tenSach.setText(item.getTenSach());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sach_spinner, null);
        }
        final Sach item = list.get(position);
        if (item!=null){
            tv_maSach = v.findViewById(R.id.tv_MaSachSP);
            tv_maSach.setText(item.getMaSach() + ". ");
            tv_tenSach = v.findViewById(R.id.tv_TenSachSP);
            tv_tenSach.setText(item.getTenSach());
        }
        return v;
    }
}
