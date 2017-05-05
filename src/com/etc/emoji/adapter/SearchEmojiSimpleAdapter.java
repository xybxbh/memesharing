package com.etc.emoji.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etc.emoji.R;
import com.etc.emoji.entity.Emoji;

import org.xutils.x;

import java.util.List;

public class SearchEmojiSimpleAdapter extends BaseAdapter {

    List<Emoji> list;
    Context context;
    Emoji emoji;
    private String urlString = "http://10.0.2.2:8080/Emoji/image/emoji/";
    public SearchEmojiSimpleAdapter(Context context, List<Emoji> list) {
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
        view = View.inflate(context, R.layout.search_listview,
                null);
        ImageView imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        TextView txtTagname = (TextView) view.findViewById(R.id.txtTagname);
        emoji=list.get(i);
        String picname = emoji.getEmojiphoto();
        x.image().bind(imgSearch, urlString + picname);
        txtTagname.setText(emoji.getEmojiinfo());
        return view;




    }
}
