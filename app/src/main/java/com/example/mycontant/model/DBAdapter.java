package com.example.mycontant.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {

    private static final String DB_NAME = "people.db";
    private static final String DB_TABLE = "peopleinfo";
    private static final int DB_VERSION = 1;
    public static final String KEY_ID = "id";
    public static final String PHONE = "phonenumber";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String COMPANY = "company";
    public static final String EMAIL = "Email";


    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context _context) {
        context = _context;
        dbOpenHelper = new DBOpenHelper(context);
    }


    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        private static final String DB_CREATE = "create table " + DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                PHONE + "  integer,  " + NAME + "  text not null, " + EMAIL + "  text," + COMPANY + "  text, " + ADDRESS + " text);";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }
    }


    /**
     * 添加一条记录
     */
    public void add(String title, String phonenumber
            , String Email, String company, String address) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("name", title);
            values.put("phonenumber", phonenumber);
            values.put("Email", Email);
            values.put("company", company);
            values.put("address", address);
            //不允许插入一个空值,如果contentvalue,一般第二个参
            db.insert(DB_TABLE, null, values);//通过组拼完成的添加的操作
        }
        db.close();
    }

    /**
     * 删除数据
     *
     * @param
     */
    public void delete(String name) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(DB_TABLE, "name=?", new String[]{name});
            db.close();
        }
    }


    public List<Phone_table> findAll() {
        List<Phone_table> persons = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            persons = new ArrayList<Phone_table>();

            Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Phone_table info = new Phone_table();
                info.setName(cursor.getString(cursor.getColumnIndex("name")));
                info.setPhonenumber(cursor.getString(cursor.getColumnIndex("phonenumber")));
                info.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                info.setCompany(cursor.getString(cursor.getColumnIndex("company")));
                info.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                persons.add(info);
            }
            cursor.close();
            db.close();
        }
        return persons;
    }

    /**
     * 更新
     *
     * @param name
     * @param newname
     * @param newphonenumber
     * @param newEmail
     * @param newcompany
     * @param newaddress
     */
    public void update(String name, String newname, String newphonenumber
            , String newEmail, String newcompany, String newaddress) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        if (db.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", newname);
            contentValues.put("phonenumber", newphonenumber);
            contentValues.put("Email", newEmail);
            contentValues.put("company", newcompany);
            contentValues.put("address", newaddress);
            db.update(DB_TABLE, contentValues, "name=?", new String[]{name});
            db.close();
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

}