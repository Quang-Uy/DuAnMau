package fpoly.edu.duan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "QUANLYTHUVIEN", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng loại sách
        String tLoaiSach = "CREATE TABLE LoaiSach (" +
                "    maLoai  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    tenLoai TEXT NOT NULL" + ")";
        db.execSQL(tLoaiSach);
        //Data mau
        db.execSQL("INSERT INTO LoaiSach VALUES(1, 'Cổ Tích')," +
                "(2, 'Tiểu Thuyết')," +
                "(3, 'Kinh Dị')");

        //Table Phieu Muon
        //Tạo >10 phiếu mượn để lấy top 10
        String tPhieuMuon = "CREATE TABLE PhieuMuon (" +
                "    maPM    INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    maTT    TEXT    REFERENCES ThuThu (maTT)," +
                "    maTV    INTEGER    REFERENCES ThanhVien (maTV)," +
                "    maSach  INTEGER REFERENCES Sach (maSach)," +
                "    giaThue INTEGER NOT NULL," +
                "    ngay    DATE    NOT NULL," +
                "    traSach INTEGER NOT NULL" +
                ");";
        db.execSQL(tPhieuMuon);
        db.execSQL("INSERT INTO PhieuMuon VALUES(1, 1, 2, 1, '150000', '28/12/2023', 1)," +
                "(2, 3, 1, 3, '200000', '14/10/2023', 2)," +
                "(3, 3, 1, 3, '200000', '14/10/2023', 2)," +
                "(4, 3, 1, 3, '200000', '14/10/2023', 2)," +
                "(5, 3, 1, 3, '200000', '14/10/2023', 2)," +
                "(6, 2, 3, 2, '500000', '05/07/2023', 1)");

        //Table Sach
        String tSach = "CREATE TABLE Sach (" +
                "    maSach  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    tenSach TEXT    NOT NULL," +
                "    giaThue INTEGER NOT NULL," +
                "    maLoai  INTEGER REFERENCES LoaiSach (maLoai)" +
                ");";
        db.execSQL(tSach);
        db.execSQL("INSERT INTO Sach VALUES(1, 'Tấm Cám', '150000', '1')," +
                "(2, 'Những đứa trẻ không bao giờ lớn', '500000','2')," +
                "(3, 'Hồ Tuyệt Mệnh', '200000', '3')");

        //Table Thanh Vien
        /*
        * role
        * 1- Nguoi dung
        * 2- thu thu
        * 3- admin
        * */
        String tThanhVien = "CREATE TABLE ThanhVien (" +
                "    maTV    INTEGER PRIMARY KEY," +
                "    hoTen   TEXT NOT NULL," +
                "    namSinh TEXT NOT NULL);";
        db.execSQL(tThanhVien);
        db.execSQL("INSERT INTO ThanhVien VALUES(1, 'Nguyễn Văn Giang', '2004')," +
                "(2, 'Nguyễn Thị Quỳnh', '1999')," +
                "(3, 'Phùng Văn Huy', '2002')");

        //Table Thu Thu
        String tThuThu = "CREATE TABLE ThuThu (" +
                "    maTT    TEXT PRIMARY KEY," +
                "    hoTen   TEXT NOT NULL," +
                "    matKhau TEXT NOT NULL" +
                ");";
        db.execSQL(tThuThu);
        db.execSQL("INSERT INTO ThuThu VALUES('PH001', 'Quang Uy', '11111')," +
                "('PH002', 'Ngọc Hiên', '22222')," +
                "('PH003', 'Quốc Phong', '33333')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            onCreate(db);
        }
    }
}
