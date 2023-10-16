package fpoly.edu.duan.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.duan.Adapter.LoaiSachSpinnerAdapter;
import fpoly.edu.duan.Adapter.SachAdapter;
import fpoly.edu.duan.DAO.LoaiSachDAO;
import fpoly.edu.duan.DAO.SachDAO;
import fpoly.edu.duan.DTO.LoaiSach;
import fpoly.edu.duan.DTO.Sach;
import fpoly.edu.duan.R;

public class SachFragment extends Fragment {
    List<Sach> list;
    FloatingActionButton float_addSach;
    Dialog dialog;
    EditText edt_MaSach, edt_TenSach, edt_GiaThue;
    Spinner spinner;
    Button btn_addSach, btn_huySach;
    ListView lvSach;
    SachDAO sachDAO;
    SachAdapter adapter;
    Sach item;

    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach, position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        lvSach = v.findViewById(R.id.lvSach);
        sachDAO = new SachDAO(getActivity());
        capNhatSach();
        float_addSach = v.findViewById(R.id.float_addSach);
        float_addSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });

        return v;
    }

    void capNhatSach() {
        list = (List<Sach>) sachDAO.getAll();
        adapter = new SachAdapter(getActivity(), this, list);
        lvSach.setAdapter(adapter);
    }

    public void xoaSach(String Id) {
        //Sử dụng alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Gọi id delete
                sachDAO.delete(Id);
                //Cập nhật lại LV
                capNhatSach();
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

    protected void openDialog(final Context context, int type){
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sach);
        edt_MaSach = dialog.findViewById(R.id.edt_MaSach);
        edt_TenSach = dialog.findViewById(R.id.edt_tenSach);
        edt_GiaThue = dialog.findViewById(R.id.edt_GiaThue);
        spinner = dialog.findViewById(R.id.spn_LoaiSach);
        btn_addSach = dialog.findViewById(R.id.btn_addSach);
        btn_huySach = dialog.findViewById(R.id.btn_huySach);

        listLoaiSach = new ArrayList<LoaiSach>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();

        spinnerAdapter = new LoaiSachSpinnerAdapter(context, listLoaiSach);
        spinner.setAdapter(spinnerAdapter);
        //Lấy mã loại sách
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
                Toast.makeText(context, "Chọn "+listLoaiSach.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Kiểm tra type
        edt_MaSach.setEnabled(false);
        if (type != 0){
            edt_MaSach.setText(String.valueOf(item.getMaSach()));
            edt_TenSach.setText(item.getTenSach());
            edt_GiaThue.setText(String.valueOf(item.getGiaThue()));
            for (int i = 0; i < listLoaiSach.size(); i++)
                if (item.getMaLoai() == (listLoaiSach.get(i).getMaLoai())){
                    position = i;
                }
            Log.i("demo","posSach"+position);
            spinner.setSelection(position);
        }
        btn_huySach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_addSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new Sach();
                item.setTenSach(edt_TenSach.getText().toString());
                item.setGiaThue(Integer.parseInt(edt_GiaThue.getText().toString()));
                item.setMaLoai(maLoaiSach);
                if (KiemTra()>0){
                    if (type==0){
                        //Type = 0 (insert)
                        if (sachDAO.insert(item)>0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Type = 1 (update)
                        item.setMaSach(Integer.parseInt(edt_MaSach.getText().toString()));
                        if (sachDAO.update(item)>0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatSach();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int KiemTra() {
        int check = 1;
        if (edt_TenSach.getText().length()==0 || edt_GiaThue.getText().length()==0){
            Toast.makeText(getContext(), "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
