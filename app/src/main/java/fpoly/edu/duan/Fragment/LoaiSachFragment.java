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

import fpoly.edu.duan.Adapter.LoaiSachAdapter1;
import fpoly.edu.duan.DAO.LoaiSachDAO;
import fpoly.edu.duan.DTO.LoaiSach;
import fpoly.edu.duan.R;

public class LoaiSachFragment extends Fragment {
    ListView lv_LoaiSach;
    ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    LoaiSachAdapter1 adapter;
    LoaiSach item;
    FloatingActionButton float_addLS;
    Dialog dialog;
    EditText edt_MaLoai, edt_tenLoai;

    Button btn_addLS, btn_huyLS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loaisach, container, false);

        //Thiết kế giao diện
        lv_LoaiSach = v.findViewById(R.id.lv_LoaiSach);
        float_addLS = v.findViewById(R.id.float_addLS);
        loaiSachDAO = new LoaiSachDAO(getActivity());
        //Nhấn float
        float_addLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd(getActivity(), 0);//Lenh add
            }
        });
        //Giữ item DS
        lv_LoaiSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                showDialogAdd(getActivity(),1);//Lenh update

                return false;
            }
        });
        loadData();
        return v;
    }

    private void loadData(){
        //Adapter
        list = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        adapter = new LoaiSachAdapter1(getActivity(), this, list);
        lv_LoaiSach.setAdapter(adapter);
    }

    public void xoaLS(String Id) {
        //Sử dụng alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Gọi id delete
                loaiSachDAO.delete(Id);
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

    protected void showDialogAdd(final Context context, int type){
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loaisach);
        //ÁNh xạ
        edt_MaLoai = dialog.findViewById(R.id.edt_MaLoai);
        edt_tenLoai = dialog.findViewById(R.id.edt_TenLoai);
        btn_addLS = dialog.findViewById(R.id.btn_addLS);
        btn_huyLS = dialog.findViewById(R.id.btn_huyLS);

        //Kiểm tra type insert = 0 hay 1
        edt_MaLoai.setEnabled(false);
        if (type != 0) {
            edt_MaLoai.setText(String.valueOf(item.getMaLoai()));
            edt_tenLoai.setText(String.valueOf(item.getTenLoai()));
        }
        btn_huyLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_addLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new LoaiSach();
                //Xét dữ liệu
                item.setTenLoai(edt_tenLoai.getText().toString());

                if (KiemTra() > 0) {
                    if (type == 0) {
                        //type = 0 sẽ gọi lệnh thêm
                        if (loaiSachDAO.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type = 1 sẽ gọi lệnh sửa
                        item.setMaLoai(Integer.parseInt(edt_MaLoai.getText().toString()));
                        if (loaiSachDAO.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    loadData();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int KiemTra() {
        int check = 1;
        if (edt_tenLoai.getText().length()==0) {
            Toast.makeText(getContext(), "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
