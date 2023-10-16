package fpoly.edu.duan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import fpoly.edu.duan.Fragment.AddUserFragment;
import fpoly.edu.duan.Fragment.DoanhThuFragment;
import fpoly.edu.duan.Fragment.LoaiSachFragment;
import fpoly.edu.duan.Fragment.PassFragment;
import fpoly.edu.duan.Fragment.PhieuMuonFragment;
import fpoly.edu.duan.Fragment.SachFragment;
import fpoly.edu.duan.Fragment.ThanhVienFragment;
import fpoly.edu.duan.Fragment.TopFragment;

public class QLTVActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    View mHeaderView;
    TextView txtUser;
    private static final int FRAGMENT_QLS = 0;
    private static final int FRAGMENT_QLLS = 1;
    private static final int FRAGMENT_QLPM = 2;
    private static final int FRAGMENT_QLTV = 3;
    private static final int FRAGMENT_TOP = 4;
    private static final int FRAGMENT_DOANHTHU = 5;
    private static final int FRAGMENT_ADDUSER = 6;
    private static final int FRAGMENT_PASS = 7;
    private static final int FRAGMENT_DANGXUAT = 8;

    private int mCurrenFragment = FRAGMENT_QLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltvactivity);

        //Sử dụng toolbar
        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Fragment chính
        FragmentManager manager = getSupportFragmentManager();
        SachFragment sachFragment = new SachFragment();
        manager.beginTransaction().replace(R.id.content_frame,sachFragment).commit();

        //Code bắt sk khi click vào item nav
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        replaceFragment(new SachFragment());
        navigationView.getMenu().findItem(R.id.nav_qls).setChecked(true);

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Click item
        if (id == R.id.nav_qls) {
            if (mCurrenFragment != FRAGMENT_QLS) {
                replaceFragment(new SachFragment());
                mCurrenFragment = FRAGMENT_QLS;
            }
        } else if (id == R.id.nav_qlls) {
            if (mCurrenFragment != FRAGMENT_QLLS) {
                replaceFragment(new LoaiSachFragment());
                mCurrenFragment = FRAGMENT_QLLS;
            }
        } else if (id == R.id.nav_qlpm) {
            if (mCurrenFragment != FRAGMENT_QLPM) {
                replaceFragment(new PhieuMuonFragment());
                mCurrenFragment = FRAGMENT_QLPM;
            }
        } else if (id == R.id.nav_qltv) {
            if (mCurrenFragment != FRAGMENT_QLTV) {
                replaceFragment(new ThanhVienFragment());
                mCurrenFragment = FRAGMENT_QLTV;
            }
        } else if (id == R.id.nav_top) {
            if (mCurrenFragment != FRAGMENT_TOP) {
                replaceFragment(new TopFragment());
                mCurrenFragment = FRAGMENT_TOP;
            }
        } else if (id == R.id.nav_doanhthu) {
            if (mCurrenFragment != FRAGMENT_DOANHTHU) {
                replaceFragment(new DoanhThuFragment());
                mCurrenFragment = FRAGMENT_DOANHTHU;
            }
        } else if (id == R.id.nav_adduser) {
            if (mCurrenFragment != FRAGMENT_ADDUSER) {
                replaceFragment(new AddUserFragment());
                mCurrenFragment = FRAGMENT_ADDUSER;
            }
        } else if (id == R.id.nav_doimatkhau) {
            if (mCurrenFragment != FRAGMENT_PASS) {
                replaceFragment(new PassFragment());
                mCurrenFragment = FRAGMENT_PASS;
            }
        } else if (id == R.id.nav_dangxuat) {
            startActivity(new Intent(QLTVActivity.this, LoginActivity.class));
        }

        //Thay đổi Tilte trên Toolbar
        getSupportActionBar().setTitle(item.getTitle());

        //Đóng drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Ấn back ko bị thoát app
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

}