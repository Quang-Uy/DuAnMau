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

import fpoly.edu.duan.DTO.ThanhVien;
import fpoly.edu.duan.R;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private ArrayList<ThanhVien> list;
    TextView tv_maTV, tv_tenTV;

    public ThanhVienSpinnerAdapter(@NonNull Context context, ArrayList<ThanhVien> list) {
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
            v = inflater.inflate(R.layout.item_thanhvien_spinner, null);
        }
        final ThanhVien item = list.get(position);
        if (item!=null){
            tv_maTV = v.findViewById(R.id.tv_MaTVSP);
            tv_maTV.setText(item.getMaTV() + ". ");
            tv_tenTV = v.findViewById(R.id.tv_TenTVSP);
            tv_tenTV.setText(item.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_thanhvien_spinner, null);
        }
        final ThanhVien item = list.get(position);
        if (item!=null){
            tv_maTV = v.findViewById(R.id.tv_MaTVSP);
            tv_maTV.setText(item.getMaTV() + ". ");
            tv_tenTV = v.findViewById(R.id.tv_TenTVSP);
            tv_tenTV.setText(item.getHoTen());
        }
        return v;
    }
}
