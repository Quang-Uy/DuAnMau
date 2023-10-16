package fpoly.edu.duan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.edu.duan.DAO.SachDAO;
import fpoly.edu.duan.DAO.ThanhVienDAO;
import fpoly.edu.duan.DTO.PhieuMuon;
import fpoly.edu.duan.DTO.Sach;
import fpoly.edu.duan.DTO.ThanhVien;
import fpoly.edu.duan.Fragment.PhieuMuonFragment;
import fpoly.edu.duan.R;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {

    private Context context;
    PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> list;
    TextView tv_MaPM, tv_TenTVPM, tv_TenSach, tv_TienThue, tv_Ngay, tv_traSach;
    ImageView img_Del;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_phieumuon, null);
        }

        final PhieuMuon item = list.get(position);
        if (item != null){
            tv_MaPM = v.findViewById(R.id.tv_MaPM);
            tv_MaPM.setText("Mã phiếu: "+item.getMaPM());

            sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getID(String.valueOf(item.getMaSach()));
            tv_TenSach= v.findViewById(R.id.tv_TenSach);
            tv_TenSach.setText("Tên sách: "+sach.getTenSach());
            thanhVienDAO = new ThanhVienDAO(context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(item.getMaTV()));
            tv_TenTVPM = v.findViewById(R.id.tv_TenTVPM);
            tv_TenTVPM.setText("Thành viên: "+thanhVien.getHoTen());
            tv_TienThue = v.findViewById(R.id.tv_giaThuePM);
            tv_TienThue.setText("Giá thuê: "+item.getGiaThue());
            tv_Ngay = v.findViewById(R.id.tv_NgayPM);
            //*****
            tv_Ngay.setText("Ngày thuê: "+sdf.format(item.getNgay()));
            tv_traSach = v.findViewById(R.id.tv_TraSach);
            if (item.getTraSach()==1){
                tv_traSach.setTextColor(Color.BLUE);
                tv_traSach.setText("Đã trả sách");
            } else {
                tv_traSach.setTextColor(Color.RED);
                tv_traSach.setText("Chưa trả sách");
            }
            img_Del = v.findViewById(R.id.img_DelPM);
        }
        img_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phương thức xóa
                fragment.xoa(String.valueOf(item.getMaPM()));
            }
        });
        return v;
    }
}
