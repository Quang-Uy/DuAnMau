package fpoly.edu.duan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.edu.duan.DTO.Top;
import fpoly.edu.duan.Fragment.TopFragment;
import fpoly.edu.duan.R;

public class TopAdapter extends ArrayAdapter<Top> {
    private Context context;
    TopFragment topFragment;
    ArrayList<Top> list;
    TextView tvSach, tvSL;
    ImageView imgDel;

    public TopAdapter(@NonNull Context context, TopFragment topFragment, ArrayList<Top> list) {
        super(context, 0,list);
        this.context = context;
        this.topFragment = topFragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_top, null);
        }
        final Top item = list.get(position);
        if (item != null){
            tvSach = v.findViewById(R.id.tvSach);
            tvSach.setText("Sách: "+item.getTenSach());
            tvSL = v.findViewById(R.id.tvSL);
            tvSL.setText("Số lượng: "+item.getSoLuong());
        }
        return v;
    }
}
