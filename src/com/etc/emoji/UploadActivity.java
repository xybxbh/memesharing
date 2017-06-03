package com.etc.emoji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.task.ImageUpload;
import com.etc.emoji.utils.StringUtil;

public class UploadActivity extends Activity {

    private ImageView imgUpload;

    private EditText edtuploadinfo;
    private Uri uri;
    Context context;
    String filename;
    String info;
    boolean test=false;
    private emojiApp app;
    private String url1 = "http://139.199.158.77:8080/Emoji/UploadMaterialServlet";
    private String url2 = "http://139.199.158.77:8080/Emoji/UploadEmojiServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imgUpload = (ImageView)findViewById(R.id.imgUpload);

        edtuploadinfo = (EditText)findViewById(R.id.edtuploadinfo);
       
        app = (emojiApp)getApplication();
    }
    public void back(View V)
    {
        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
        startActivity(intent);
        
       
    }
    public void selectfromalbum(View V)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            uri = data.getData();
            imgUpload.setImageURI(uri);
            test=true;

        }
         context = this.getApplicationContext();
         filename = StringUtil.getRealPathFromURI(context, uri);
      //   Toast.makeText(context,filename, Toast.LENGTH_SHORT).show();

    }
    public void uploadtomaterial(View V)
    {
    	if(test)
    	{
    		info = edtuploadinfo.getText().toString();
            new ImageUpload().execute(url1, filename, info, Integer.toString(app.getUser().getUserid()));
            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
            startActivity(intent);
    	}
    	
    	 
      
        
        
    }
    public void uploadtoemoji(View V)
    {
    	if(test)
    	{
    		info = edtuploadinfo.getText().toString();
            new ImageUpload().execute(url2, filename, info, Integer.toString(app.getUser().getUserid()));
           
            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
            startActivity(intent);
    	}
    	else
    	{
    		Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
    	}
    	 
    }

}
