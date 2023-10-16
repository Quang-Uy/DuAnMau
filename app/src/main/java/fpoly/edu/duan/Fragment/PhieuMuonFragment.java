package fpoly.edu.duan.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.edu.duan.Adapter.PhieuMuonAdapter;
import fpoly.edu.duan.Adapter.SachSpinnerAdapter;
import fpoly.edu.duan.Adapter.ThanhVienSpinnerAdapter;
import fpoly.edu.duan.DAO.PhieuMuonDAO;
import fpoly.edu.duan.DAO.SachDAO;
import fpoly.edu.duan.DAO.ThanhVienDAO;
import fpoly.edu.duan.DTO.PhieuMuon;
import fpoly.edu.duan.DTO.Sach;
import fpoly.edu.duan.DTO.ThanhVien;
import fpoly.edu.duan.R;

public class PhieuMuonFragment extends Fragment {
    ListView lv_PhieuMuon;
    ArrayList<PhieuMuon> list;
    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon item;

    //Dialog
    FloatingActionButton float_addPM;
    Dialog dialog;
    EditText edt_maPM;
    Spinner spnTV, spnSach;
    TextView tv_Ngay, tv_giaThue;
    CheckBox chk_traSach;
    Button btn_addPM, btn_huyPM;
    //********* Spinner TV
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien> listTV;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int maThanhVien;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //********* Spinner Sach
    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach, giaThue;
    int positionTV, positionSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phieumuon, container, false);
        lv_PhieuMuon = v.findViewById(R.id.lv_PhieuMuon);
        float_addPM = v.findViewById(R.id.float_addPM);
        dao = new PhieuMuonDAO(getActivity());
        float_addPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(), 0);//Insert
            }
        });
        lv_PhieuMuon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                showDialog(getActivity(),1);//Update
                return false;
            }
        });

        loadData();
        return v;
    }

    void loadData(){
        list = (ArrayList<PhieuMuon>) dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), this,list);
        lv_PhieuMuon.setAdapter(adapter);
    }

    protected void showDialog(final Context context, int type){
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_phieumuon);
        edt_maPM = dialog.findViewById(R.id.edt_MaPM);
        edt_maPM.setEnabled(false);
        spnTV = dialog.findViewById(R.id.spn_MaTV);
        spnSach = dialog.findViewById(R.id.spn_MaSach);
        tv_Ngay = dialog.findViewById(R.id.tv_ngay);
        tv_giaThue = dialog.findViewById(R.id.tv_giaThue);
        chk_traSach = dialog.findViewById(R.id.chk_traSach);
        btn_addPM = dialog.findViewById(R.id.btn_addPM);
        btn_huyPM = dialog.findViewById(R.id.btn_huyPM);
        //set Ngay thue mac dinh
        tv_Ngay.setText("Ngày thuê: "+sdf.format(new Date()));

        //Spinner TV
        thanhVienDAO = new ThanhVienDAO(context);
        listTV = new ArrayList<ThanhVien>();
        listTV = (ArrayList<ThanhVien>)thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context,listTV);
        spnTV.setAdapter(thanhVienSpinnerAdapter);
        spnTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listTV.get(position).getMaTV();
                //Toast.makeText(context, "Chọn "+listTV.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Sach
        sachDAO = new SachDAO(context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>)sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context, listSach);
        spnSach.setAdapter(sachSpinnerAdapter);
        //Lấy mã loại sách
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                giaThue = listSach.get(position).getGiaThue();
                tv_giaThue.setText("Giá thuê: "+giaThue);
                //Toast.makeText(context, "Chọn "+listSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Edit set data
        if (type != 0){
            edt_maPM.setText(String.valueOf(item.getMaPM()));
            for (int i = 0; i < listTV.size(); i++)
                if (item.getMaTV() == (listTV.get(i).getMaTV())){
                    positionTV = i;
                }
            spnTV.setSelection(positionTV);

            for (int i = 0; i < listSach.size(); i++)
                if (item.getMaSach() == (listSach.get(i).getMaSach())){
                    positionTV = i;
                }
            spnSach.setSelection(positionSach);
            tv_Ngay.setText("Ngày thuê: "+sdf.format(item.getNgay()));
            tv_giaThue.setText("Giá thuê: "+item.getGiaThue());
            if (item.getTraSach()==1){
                chk_traSach.setChecked(true);
            } else {
                chk_traSach.setChecked(false);
            }
        }
        btn_huyPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_addPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new PhieuMuon();
                item.setMaSach(maSach);
                item.setMaTV(maThanhVien);
                item.setNgay(new Date());
                item.setGiaThue(giaThue);
                if (chk_traSach.isChecked()){
                    item.setTraSach(1);
                } else {
                    item.setTraSach(0);
                }

                if (type == 0){
                    //type = 0 (insert)
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //type = 1 (update)
                    item.setMaPM(Integer.parseInt(edt_maPM.getText().toString()));
                    if (dao.update(item)>0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                loadData();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void xoa(String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Gọi id delete
                dao.delete(Id);
                //Cập nhật lại LV
                loadData();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
}
