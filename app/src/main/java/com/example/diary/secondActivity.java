package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class secondActivity extends Activity implements View.OnClickListener {
    private ImageButton btn_back,btn_save,btn_delete;
    private EditText editText1,editText2,editText3;
    private SQLiteDatabase db;
    private DatabaseHelper helper;
    private boolean flag=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //创建数据库
        helper = new DatabaseHelper(this);
        bindView();
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

    public int getidmax(){
        int x=0;
        Cursor cursor = db.query("text", null, null, null, null, null, "_id DESC");
        if(cursor.moveToNext())
        {
            // 这个id就是最大值
            x = cursor.getInt(cursor.getColumnIndex("_id"));
        }
        return x;
    }

    public void insert() {
        String name=editText3.getText().toString();
        if(name.equals(""))
        {
            //获得默认作者
            Intent intent=getIntent();
            name =intent.getStringExtra("name");
        }

        String title = editText1.getText().toString();
        String content = editText2.getText().toString();
        if(!title.equals("")||!content.equals(""))
        {
            String sql="insert into "+DatabaseHelper.DB_NAME+"(_title,_content,_version,_editer) values(?,?,1,?)";
            db.execSQL(sql,new Object[]{title,content,name});
            Toast.makeText(this, "插入成功！", Toast.LENGTH_SHORT).show();
            db.close();
        }
        flag=true;
    }

    private void update() {
        String title = editText1.getText().toString();
        String content = editText2.getText().toString();
        String editer=editText3.getText().toString();
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        int id=getidmax();
        if(editer.equals(""))
        {
            editer=name;
        }
        String sql="update "+DatabaseHelper.DB_NAME+ " set _title = '"+ title + "',_content = '"+ content + "',_editer = '"+ editer + "' where _id = "+id;
        db.execSQL(sql);
        Toast.makeText(this, "更新成功！", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void save(){
        if(!flag){
            insert();
        }
        else if(flag){
            update();
        }
    }

    public void toback(){
        Intent intent= new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        db = helper.getWritableDatabase();
        switch (v.getId()){
            case R.id.save:
                save();
                break;
            case R.id.back:
                save();
                toback();
                break;
            case R.id.delete:
                toback();
                break;
        }
    }
}
