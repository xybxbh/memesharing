package com.etc.emoji;

import org.xutils.x;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.task.AddCommentTask;
import com.etc.emoji.task.ImageUpload;
import com.etc.emoji.task.UploadNewEmojiTask;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Build;

public class UploadNewEmojiActivity extends Activity {
	
	private String path;
	private int materialid;
	private EditText edtUploadNew;
	private ImageView imgNewEmoji;
	private emojiApp app;
	private String url = "http://139.199.158.77:8080/Emoji/UploadNewEmojiServlet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_new_emoji);
		
		
		 app = (emojiApp)getApplication();
		edtUploadNew = (EditText)findViewById(R.id.edtUploadNew);
		imgNewEmoji = (ImageView)findViewById(R.id.imgNewEmoji);
		 Intent intent = this.getIntent();
		 path = intent.getStringExtra("path");
		 materialid = Integer.parseInt(intent.getStringExtra("materialid"));
		 x.image().bind(imgNewEmoji, path);

		
	}
	  public void back(View V)
	    {
	        finish();
	    }

	    public void upload(View V)
	    {
	    	String info = edtUploadNew.getText().toString();
	    	
	    	 new UploadNewEmojiTask().execute(url, path, info, materialid+"",Integer.toString(app.getUser().getUserid()));
	         
	         Intent intent = new Intent(UploadNewEmojiActivity.this, MainActivity.class);
	         startActivity(intent);
	       

	    }

	
	

}
