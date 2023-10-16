package fpoly.edu.duan.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.edu.duan.Adapter.ThanhVienAdapter;
import fpoly.edu.duan.DAO.ThanhVienDAO;
import fpoly.edu.duan.DTO.ThanhVien;
import fpoly.edu.duan.R;

public class ThanhVienFragment extends Fragment {
    ListView lvThanhVien;
    ArrayList<ThanhVien> list;
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;
    FloatingActionButton float_addTV;
    Dialog dialog;
    EditText edt_MaTV, edt_TenTV, edt_NamSinh;
    Button btn_addTV, btn_huyTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thanhvien, container, false);
        lvThanhVien = v.findViewById(R.id.lvThanhVien);
        float_addTV = v.findViewById(R.id.float_addTV);
        dao = new ThanhVienDAO(getActivity());
        capNhatLV();
        //Nhấn float
        float_addTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);//Lenh add
            }
        });
        //Giữ item DS
        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);//Lenh update

                return false;
            }
        });

        return v;
    }

    //cập nhật dữ liệu lên ListView
    void capNhatLV(){
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(), this, list);
        lvThanhVien.setAdapter(adapter);
    }

    public void xoaTV(String Id) {
        //Sử dụng alertdialog
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
                capNhatLV();
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

    protected void openDialog(final Context context, int type) {
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thanhvien);
        //ÁNh xạ
        edt_MaTV = dialog.findViewById(R.id.edt_MaTV);
        edt_TenTV = dialog.findViewById(R.id.edt_TenTV);
        edt_NamSinh = dialog.findViewById(R.id.edt_NamSinh);
        btn_addTV = dialog.findViewById(R.id.btn_addTV);
        btn_huyTV = dialog.findViewById(R.id.btn_huyTV);

        //Kiểm tra type insert = 0 hay 1
        edt_MaTV.setEnabled(false);
        if (type != 0) {
            edt_MaTV.setText(String.valueOf(item.getMaTV()));
            edt_TenTV.setText(String.valueOf(item.getHoTen()));
            edt_NamSinh.setText(String.valueOf(item.getNamSinh()));
        }
        btn_huyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_addTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new ThanhVien();
                //Xét dữ liệu
                item.setHoTen(edt_TenTV.getText().toString());
                item.setNamSinh(edt_NamSinh.getText().toString());
                if (KiemTra()>0){
                    if (type==0){
                        //type = 0 sẽ gọi lệnh thêm
                        if (dao.insert(item)>0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type = 1 sẽ gọi lệnh sửa
                        item.setMaTV(Integer.parseInt(edt_MaTV.getText().toString()));
                        if (dao.update(item)>0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLV();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int KiemTra() {
        int check = 1;
        if (edt_TenTV.getText().length()==0 || edt_NamSinh.getText().length()==0) {
            Toast.makeText(getContext(), "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
