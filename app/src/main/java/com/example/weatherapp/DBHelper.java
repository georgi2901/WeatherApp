package com.example.weatherapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class DBHelper extends AppCompatActivity {
    protected interface OnQuerySuccess{
        public void OnSuccess();
    }
    protected interface OnSelectSuccess{
        public void OnElementSelected(
                String ID, String Name, String Type
        );
    }

    protected void SelectSQL(String SelectQ, String[] args, OnSelectSuccess success)
        throws Exception{
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/HISTORY.db", null);
        Cursor cursor = db.rawQuery(SelectQ, args);
        while (cursor.moveToNext()){
            String ID = cursor.getString(cursor.getColumnIndex("ID"));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Type = cursor.getString(cursor.getColumnIndex("Type"));
            success.OnElementSelected(ID, Name, Type);
        }
        db.close();
    }

    protected void  ExecSQL(String SQL, Object[] args, OnQuerySuccess success)
        throws Exception{
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/HISTORY.db", null);
        if(args != null)
            db.execSQL(SQL, args);
        else
            db.execSQL(SQL);

        db.close();
        success.OnSuccess();
    }
    protected void initDB() throws Exception{
        ExecSQL(
                "CREATE TABLE if not exists HISTORY( " +
                        "ID integer PRIMARY KEY AUTOINCREMENT, " +
                        "Name text not null, " +
                        "Type text not null " +
                        ")",
                null, ()-> Toast.makeText(getApplicationContext(), "DB Init Successful", Toast.LENGTH_SHORT).show()
        );
    }

}
