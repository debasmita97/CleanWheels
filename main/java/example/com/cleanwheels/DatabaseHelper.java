package example.com.cleanwheels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "BOOKINGID";
    public static final String COL_2 = "ADDRESS";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "PHONE";
    public static final String COL_5 = "AMOUNT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (BOOKINGID INTEGER PRIMARY KEY AUTOINCREMENT,ADDRESS TEXT,DATE TEXT,PHONE TEXT, AMOUNT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String address,String date,String phone, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,address);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,phone);
        contentValues.put(COL_5, amount);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getData(String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_4 + " = '" + phone + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean updateData(String bookingid,String address,String date,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,bookingid);
        contentValues.put(COL_2,address);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,phone);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { bookingid });
        return true;
    }

    public Integer deleteData (String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "PHONE = ?",new String[] {phone});
    }
}