package com.etc.emoji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.entity.Emoji;
import com.etc.emoji.task.AddCommentTask;

public class AddCommentActivity extends Activity {
    private EditText edtComment;
    private emojiApp app;
    String comment;
    int emojiid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        Intent intent = this.getIntent();
        emojiid = Integer.parseInt(intent.getStringExtra("emojiid"));
        

        edtComment = (EditText)findViewById(R.id.edtComment);
        app = (emojiApp) getApplication();

    }

    public void back(View V)
    {
        finish();
    }

    public void addcomment(View V)
    {
        comment = edtComment.getText().toString();

        new AddCommentTask(this).execute(comment,app.getUser().getUserid()+"",emojiid+"");

        finish();

    }

}
