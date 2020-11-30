package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class thirdActivity extends Activity implements View.OnClickListener {
    private ImageButton btn_back,btn_save,btn_delete;
    private EditText editText1,editText2,editText3;
    private SQLiteDatabase db;
    private DatabaseHelper helper;
    private String tag="thirdActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //创建数据库
        helper = new DatabaseHelper(this);
        bindView();
        init();
    }

    private void init() {
        Intent intent=getIntent();
        int id =intent.getIntExtra("position",0);
        Log.d(tag, "c！"+id);
        Toast.makeText(this,"c"+id , Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql="select * from "+DatabaseHelper.DB_NAME+" where _id = "+id;
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("_title"));
            String content = cursor.getString(cursor.getColumnIndex("_content"));
            String editer = cursor.getString(cursor.getColumnIndex("_editer"));
            editText1.setText(title);
            editText2.setText(content);
            editText3.setText(editer);
        }
        cursor.close();
        db.close();
    }

    private void bindView() {
        btn_back=findViewById(R.id.back);
        btn_save=findViewById(R.id.save);
        btn_delete=findViewById(R.id.delete);

        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        editText1=findViewById(R.id.sv_title);
        editText2=findViewById(R.id.sv_content);
        editText3=findViewById(R.id.sv_editer);
    }

    private void update() {
        Intent intent=getIntent();
        Integer id =intent.getIntExtra("position",0);
        String name=intent.getStringExtra("name");
        String title = editText1.getText().toString();
        String content = editText2.getText().toString();
        String editer=editText3.getText().toString();
        if(editer.equals(""))
        {
            editer=name;
        }
        String sql="update "+DatabaseHelper.DB_NAME+ " set _title = '"+ title + "',_content = '"+ content + "',_editer = '"+ editer + "' where _id = "+id;
        db.execSQL(sql);
        Toast.makeText(this, "更新成功！", Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void delete() {
        Intent intent=getIntent();
        Integer id =intent.getIntExtra("position",0);
        String sql="delete from "+DatabaseHelper.DB_NAME+ " where _id = "+id;
        db.execSQL(sql);
        String sql1="update "+DatabaseHelper.DB_NAME+ " set _id = _id - 1 where _version = 1 and _id > "+id;
        db.execSQL(sql1);
        Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
    }

    public  void toback() {
        Intent intent= new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        db = helper.getWritableDatabase();
        String title = editText1.getText().toString();
        String content = editText2.getText().toString();
        switch (v.getId()){
            case R.id.save:
                if(title.equals("")&&content.equals(""))
                {
                    editText1.setText("未命名");
                    update();
                    toback();
                    break;
                }
                update();
                break;
            case R.id.back:
                if(title.equals("")&&content.equals(""))
                {
                    delete();
                    toback();
                    break;
                }
                update();
                toback();
                break;
            case R.id.delete:
                delete();
                toback();
                break;
        }
    }




}
