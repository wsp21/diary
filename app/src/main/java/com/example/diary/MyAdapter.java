package com.example.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;


class MyAdapter extends BaseAdapter {
    private List<Map<String,String>> mlist;
    private Context mContext;
    private LayoutInflater mlayoutInflater;

    public MyAdapter(Context mContext, List<Map<String, String>> list) {
        mlayoutInflater=LayoutInflater.from(mContext);
        this.mContext=mContext;
        this.mlist=list;
    }


    @Override
    public int getCount() {
        return mlist == null ? 0: mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mlayoutInflater.inflate(R.layout.item, parent, false);

        //获取集合中的元素
        TextView title = view.findViewById(R.id.tv_title);
        TextView content = view.findViewById(R.id.tv_content);
        TextView date = view.findViewById(R.id.tv_date);
        TextView editer = view.findViewById(R.id.tv_editer);

        title.setText((String) mlist.get(position).get("title").toString());
        content.setText((String) mlist.get(position).get("content").toString());
        date.setText((String) mlist.get(position).get("date").toString());
        editer.setText("作者："+(String) mlist.get(position).get("editer").toString());
        return view;
    }
}
