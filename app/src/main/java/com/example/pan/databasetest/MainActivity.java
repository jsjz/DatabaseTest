package com.example.pan.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getReadableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "Game of the Throne");
                values.put("author", "Dan Brown");
                values.put("pages", 450);
                values.put("price", 42);
                db.insert("book", null, values);//插入第一条数据
                values.clear();
                values.put("name", "Dan Brown");
                values.put("author", "Sam Kin");
                values.put("pages", 123);
                values.put("price", 12);
                db.insert("book", null, values);//插入第二条数据
            }
        });

        Button update = (Button) findViewById(R.id.update_data);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.3);
                db.update("book", values, "name=?", new String[]{"game of the throne"});
            }
        });

        Button query = (Button) findViewById(R.id.query_data);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询book表中所有数据
                Cursor cursor = db.query("book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        //便利cursor对象
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        if (name==null){
                            Log.d("tag", "null");
                        }
                        Log.d("tag", name);
                        Log.d("tag", author);
                        Log.d("tag", String.valueOf(pages));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
