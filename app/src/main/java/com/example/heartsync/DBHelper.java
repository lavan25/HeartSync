package com.example.heartsync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "HeartSync.db";

    public DBHelper(Context context) {
        super(context, "HeartSync.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create Table users(username TEXT primary key, password TEXT, firstname TEXT, lastname TEXT, date TEXT, phonenumber TEXT, email TEXT, address TEXT, time TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    public Boolean insertdata(String username, String password, String firstname, String lastname, String date, String phonenumber, String email, String address, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("date",date);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("email",email);
        contentValues.put("address",address);
        contentValues.put("time",time);
        long result = db.insert("users",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username1, String password1){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username1,password1});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public ArrayList<UserModal> getUser(String username1){

        ArrayList<UserModal> al = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where username='"+username1+"'", null);
        if(cursor.moveToFirst())
        {
            String username = cursor.getString(0);
            String firstname = cursor.getString(2);
            String lastname = cursor.getString(3);
            String date = cursor.getString(4);
            String phonenumber = cursor.getString(5);
            String email = cursor.getString(6);
            String address = cursor.getString(7);
            String time = cursor.getString(8);

            UserModal userModal = new UserModal();
            userModal.setFirstname(firstname);
            userModal.setLastname(lastname);
            userModal.setUsername(username);
            userModal.setEmail(email);
            userModal.setPhonenumber(phonenumber);
            userModal.setDate(date);
            userModal.setAddress(address);
            userModal.setTime(time);

            al.add(userModal);
        }
        cursor.close();
        return al;
    }
}
