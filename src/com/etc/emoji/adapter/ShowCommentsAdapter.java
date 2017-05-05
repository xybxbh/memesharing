package com.etc.emoji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etc.emoji.R;
import com.etc.emoji.entity.Comment;

import java.util.List;



public class ShowCommentsAdapter extends BaseAdapter {
    List<com.etc.emoji.entity.Comment> list;
    Context context;
    Comment comment;

    public ShowCommentsAdapter(Context context, List<Comment> list) {
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
        view = View.inflate(context, R.layout.comment_listview,
                null);
        TextView txtCommentuser = (TextView) view.findViewById(R.id.txtCommentuser);
        TextView txtCommentInfo = (TextView) view.findViewById(R.id.txtCommentInfo);
        comment=list.get(i);
        txtCommentuser.setText(comment.getUser().getUsername()+":");
        txtCommentInfo.setText(comment.getCommentinfo());
        return view;

    }
}
