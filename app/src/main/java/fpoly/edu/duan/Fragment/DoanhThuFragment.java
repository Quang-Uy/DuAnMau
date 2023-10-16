package fpoly.edu.duan.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fpoly.edu.duan.DAO.PhieuMuonDAO;
import fpoly.edu.duan.R;

public class DoanhThuFragment extends Fragment {
    Button btn_TuNgay, btn_DenNgay, btn_doanhThu;
    EditText edt_TuNgay, edt_DenNgay;
    TextView tv_doanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int mYear, mMonth, mDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doanhthu, container, false);
        edt_TuNgay = v.findViewById(R.id.edt_TuNgay);
        edt_DenNgay = v.findViewById(R.id.edt_DenNgay);
        tv_doanhThu = v.findViewById(R.id.tv_doanhThu);
        btn_TuNgay = v.findViewById(R.id.btn_TuNgay);
        btn_DenNgay = v.findViewById(R.id.btn_DenNgay);
        btn_doanhThu = v.findViewById(R.id.btn_doanhThu);
        btn_TuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btn_DenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btn_doanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edt_TuNgay.getText().toString();
                String denNgay = edt_DenNgay.getText().toString();
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getActivity());
                tv_doanhThu.setText("Doanh thu: "+phieuMuonDAO.getDoanhThu(tuNgay,denNgay)+" VNĐ");
            }
        });

        return v;
    }

    //Tu ngay
    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edt_TuNgay.setText(sdf.format(c.getTime()));
        }
    };

    //Den ngay
    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edt_DenNgay.setText(sdf.format(c.getTime()));
        }
    };
}
