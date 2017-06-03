package com.etc.emoji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.etc.emoji.R;
import com.etc.emoji.entity.Material;

import org.xutils.x;

import java.util.List;

public class GridViewMaterialAdapter extends BaseAdapter {
    List<Material> list;
    Context context;
    Material material;
    
   // private String urlString = "http://139.199.158.77:8080/emoji/image/material/";
    private String urlString = "http://139.199.158.77:8080/Emoji/image/material/";
    public GridViewMaterialAdapter(Context context, List<Material> list) {
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
        material=list.get(i);
        String picname = material.getMaterialphoto();
        x.image().bind(imgGridphoto, urlString + picname);


        return view;




    }
}
