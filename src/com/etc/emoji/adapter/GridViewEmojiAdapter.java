package com.etc.emoji.adapter;


import java.util.List;

import org.xutils.x;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.etc.emoji.R;
import com.etc.emoji.entity.Emoji;

public class GridViewEmojiAdapter extends BaseAdapter {

    List<Emoji> list;
    Context context;
    Emoji emoji;
 
    private String urlString = "http://139.199.158.77:8080/Emoji/image/emoji/";
    //private String urlString = "http://139.199.158.77:8080/emoji/image/emoji/";
    public GridViewEmojiAdapter(Context context, List<Emoji> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(context, R.layout.show_gridview,
                null);
        ImageView imgGridphoto = (ImageView) view.findViewById(R.id.imgGridphoto);


        emoji=list.get(i);
        String picname = emoji.getEmojiphoto();
        x.image().bind(imgGridphoto, urlString + picname);
        return view;
    }
}
