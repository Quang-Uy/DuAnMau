package fpoly.edu.duan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.edu.duan.DAO.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {

    EditText edt_login_ten, edt_login_matkhau;
    Button btn_login;
    CheckBox remember_pass;
    String strUser, strPass;
    ThuThuDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng nhập");
        //Ánh xạ
        edt_login_ten = findViewById(R.id.edt_login_ten);
        edt_login_matkhau = findViewById(R.id.edt_login_matkhau);
        remember_pass = findViewById(R.id.remember_pass);
        btn_login = findViewById(R.id.btn_login);
        dao = new ThuThuDAO(this);

        //Đọc user, pass trong SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        String pass = pref.getString("PASSWORD","");
        Boolean rem = pref.getBoolean("REMEMBER",false);

        edt_login_ten.setText(user);
        edt_login_matkhau.setText(pass);
        remember_pass.setChecked(rem);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }});
    }

    public void checkLogin() {
        strUser = edt_login_ten.getText().toString();
        strPass = edt_login_matkhau.getText().toString();
        //Để trống thông tin
        if (strUser.isEmpty()||strPass.isEmpty()){
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (dao.checkLogin(strUser,strPass)>0) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,remember_pass.isChecked());
                Intent i = new Intent(this, QLTVActivity.class);
                i.putExtra("user", strUser);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            //Xóa dữ liệu trước đó
            edit.clear();
        } else {
            //Lưu dữ liệu
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            edit.putBoolean("REMEMBER",status);
        }
        //Lưu lại toàn bộ
        edit.commit();
    }
}