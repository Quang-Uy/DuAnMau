package fpoly.edu.duan.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import fpoly.edu.duan.DAO.ThuThuDAO;
import fpoly.edu.duan.DTO.ThuThu;
import fpoly.edu.duan.R;

public class PassFragment extends Fragment {

    TextInputEditText edt_PassOld, edt_PassChange, edt_RePassChange;
    Button btn_savePass, btn_canclePass;
    ThuThuDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pass, container, false);

        edt_PassOld = v.findViewById(R.id.edt_passOld);
        edt_PassChange = v.findViewById(R.id.edt_passChange);
        edt_RePassChange = v.findViewById(R.id.edt_RepassChange);
        btn_savePass = v.findViewById(R.id.btn_savePass);
        btn_canclePass = v.findViewById(R.id.btn_cancelPass);
        dao = new ThuThuDAO(getActivity());

        btn_canclePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_PassOld.setText("");
                edt_PassChange.setText("");
                edt_RePassChange.setText("");
            }
        });

        btn_savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Đọc user trong sharedPreferences
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");
                if (validate()>0){
                    ThuThu thuThu = dao.getID(user);
                    thuThu.setMatKhau(edt_PassChange.getText().toString());
                    if (dao.updatePass(thuThu) >0) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edt_PassOld.setText("");
                        edt_PassChange.setText("");
                        edt_RePassChange.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }

    //Hàm kiểm tra
    public int validate() {
        int check = 1;
        if (edt_PassOld.getText().length()==0 || edt_PassChange.getText().length()==0 || edt_RePassChange.getText().length()==0) {
            Toast.makeText(getActivity(), "Bạn hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            //Đọc pass trong sharedPreferences
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD", "");
            String passChange = edt_PassChange.getText().toString();
            String RepassChange = edt_RePassChange.getText().toString();
            if (!passOld.equals(edt_PassOld.getText().toString())){
                Toast.makeText(getActivity(), "Mật khẩu cũ chưa đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passChange.equals(RepassChange)) {
                Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}
