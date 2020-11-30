package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建数据库
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        //链接listView
        ListView listView = (ListView)findViewById(R.id.list1);

        //创建存贮容器
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        //循环显示布局信息
        String sql="select * from "+DatabaseHelper.DB_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext())
        {
            Map<String, String> listem = new HashMap<String, String>();
            String title=cursor.getString(cursor.getColumnIndex("_title"));
            String content=cursor.getString(cursor.getColumnIndex("_content"));
            String datetime=cursor.getString(cursor.getColumnIndex("_datetime"));
            String editer=cursor.getString(cursor.getColumnIndex("_editer"));
            listem.put("title",title);
            listem.put("content",content);
            listem.put("date",datetime);
            listem.put("editer",editer);
            list.add(listem);
        }
        MyAdapter adapter =new MyAdapter(this,list);
        listView.setAdapter(adapter);

        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position=position+1;
                Log.d(MainActivity.TAG, "点击！"+position+"行");
                change(position);
            }
        });
        db.close();
    }

    public  String setPreferences(){
        //设置默认作者
        SharedPreferences pref = MainActivity.this.getSharedPreferences("date",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name","wsp");
        editor.commit();

        //读取数据
        String name=pref.getString("name",null);
        return name;
    }

    public int change(int position) {
        String name=setPreferences();
        Intent intent= new Intent();
        intent.putExtra("name",name);
        intent.setClass(this,thirdActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
        return 0;
    }

    public void add(View view) {
        String name=setPreferences();
        Intent intent= new Intent();
        intent.putExtra("name",name);
        intent.setClass(this,secondActivity.class);
        startActivity(intent);
    }
}
