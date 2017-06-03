package com.etc.emoji;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etc.emoji.entity.Material;
import com.etc.emoji.holocolorpicker.SelectColorPopup;
import com.etc.emoji.operate.OperateConstants;
import com.etc.emoji.operate.OperateUtils;
import com.etc.emoji.operate.OperateView;
import com.etc.emoji.operate.TextObject;
import com.etc.emoji.utils.Constants;
import com.etc.emoji.utils.StringUtil;


public class AddTextActivity extends Activity implements OnClickListener
{

	private LinearLayout content_layout;
	private OperateView operateView;
	private String camera_path;
	private String mPath = null;
	OperateUtils operateUtils;
	private ImageButton btn_ok, btn_cancel;
	private Button add, color, family, moren, faceby, facebygf;
	private LinearLayout face_linear;
	private SelectColorPopup menuWindow;
	private String typeface;
	private String materialid;
	Material material;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtext);
		Intent intent = this.getIntent();
		
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
        .detectDiskReads().detectDiskWrites().detectNetwork()  
        .penaltyLog().build());  
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()  
        .penaltyLog().penaltyDeath().build());
		
		
		
		camera_path = intent.getStringExtra("camera_path");
		materialid = intent.getStringExtra("materialid");
		material = (Material)intent.getSerializableExtra("material");
		operateUtils = new OperateUtils(this);
		
		
		content_layout = (LinearLayout) findViewById(R.id.mainLayout);
		face_linear = (LinearLayout) findViewById(R.id.face_linear);
		btn_ok = (ImageButton) findViewById(R.id.btn_ok);
		btn_cancel = (ImageButton) findViewById(R.id.btn_cancel);
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		add = (Button) findViewById(R.id.addtext);
		color = (Button) findViewById(R.id.color);
		family = (Button) findViewById(R.id.family);
		add.setOnClickListener(this);
		color.setOnClickListener(this);
		family.setOnClickListener(this);

		moren = (Button) findViewById(R.id.moren);
		moren.setTypeface(Typeface.DEFAULT);
		faceby = (Button) findViewById(R.id.faceby);
		faceby.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/"
				+ OperateConstants.FACE_BY + ".ttf"));
		facebygf = (Button) findViewById(R.id.facebygf);
		facebygf.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/"
				+ OperateConstants.FACE_BYGF + ".ttf"));
		moren.setOnClickListener(this);
		faceby.setOnClickListener(this);
		facebygf.setOnClickListener(this);

	
		// 延迟每次延迟10 毫秒 隔1秒执行一次
		//timer.schedule(task, 10, 1000);
		fillContent();
	}

	
	private void fillContent()
	{
		Bitmap resizeBmp = getBitmapFromServer(camera_path);
		operateView = new OperateView(AddTextActivity.this, resizeBmp);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				resizeBmp.getWidth(), resizeBmp.getHeight());
		operateView.setLayoutParams(layoutParams);
		content_layout.addView(operateView);
		operateView.setMultiAdd(true); //设置此参数，可以添加多个文字
	}
	
	public static Bitmap getBitmapFromServer(String imagePath) {  
	      
	    HttpGet get = new HttpGet(imagePath);  
	    HttpClient client = new DefaultHttpClient();  
	    Bitmap pic = null;  
	    try {  
	        HttpResponse response = client.execute(get);  
	        HttpEntity entity = response.getEntity();  
	        InputStream is = entity.getContent();  
	          
	        pic = BitmapFactory.decodeStream(is);   // 关键是这句代码  
	          
	    } catch (ClientProtocolException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return pic;  
	}  

	private void btnSave()
	{
		operateView.save();
		Bitmap bmp = getBitmapByView(operateView);
		if (bmp != null)
		{
			String savename = StringUtil.getuploadTime()+".jpg";
			mPath = saveBitmap(bmp, savename);
			
			//Toast.makeText(this, savename, Toast.LENGTH_SHORT).show();
			//Toast.makeText(this, mPath, Toast.LENGTH_SHORT).show();
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(mPath))));
			//以上已经完成了保存到内存
			Intent intent = new Intent(AddTextActivity.this,UploadNewEmojiActivity.class);
	        intent.putExtra("path", mPath);
	        intent.putExtra("materialid", materialid);
	        startActivity(intent);
			
		}
	}

	
	// 将生成的图片保存到内存中
	public String saveBitmap(Bitmap bitmap, String name)
	{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			File dir = new File(Constants.filePath);
			if (!dir.exists())
				dir.mkdir();
			File file = new File(Constants.filePath + name);
			FileOutputStream out;

			try
			{
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
				{
					out.flush();
					out.close();
				}
				return file.getAbsolutePath();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}

		return null;
	}
	// 将模板View的图片转化为Bitmap
		public Bitmap getBitmapByView(View v)
		{
			Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			v.draw(canvas);
			return bitmap;
		}


	private void addfont()
	{
		final EditText editText = new EditText(AddTextActivity.this);
		new AlertDialog.Builder(AddTextActivity.this).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int which)
					{
						String string = editText.getText().toString();
					
						TextObject textObj = operateUtils.getTextObject(string,
								operateView, 5, 150, 100);
						if(textObj != null){
							if (menuWindow != null)
							{
								textObj.setColor(menuWindow.getColor());
							}
							textObj.setTypeface(typeface);
							textObj.commit();
							operateView.addItem(textObj);
							operateView.setOnListener(new OperateView.MyListener()
							{
								public void onClick(TextObject tObject)
								{
									alert(tObject);
								}
							});
						}
					}
				}).show();
	}
	private void alert(final TextObject tObject)
	{

		final EditText editText = new EditText(AddTextActivity.this);
		new AlertDialog.Builder(AddTextActivity.this).setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int which)
					{
						String string = editText.getText().toString();
						tObject.setText(string);
						tObject.commit();
					}
				}).show();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.color :
				menuWindow = new SelectColorPopup(AddTextActivity.this,
						AddTextActivity.this);
				// 显示窗口
				menuWindow.showAtLocation(
						AddTextActivity.this.findViewById(R.id.main),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
				break;
			case R.id.submit :
				menuWindow.dismiss();
				break;
			case R.id.family :
				if (face_linear.getVisibility() == View.GONE)
				{
					face_linear.setVisibility(View.VISIBLE);
				} else
				{
					face_linear.setVisibility(View.GONE);
				}
				break;
			case R.id.addtext :
				addfont();
				break;
			case R.id.btn_ok :
				btnSave();
				break;
			case R.id.btn_cancel :
				finish();
	
				break;
			case R.id.moren :
				typeface = null;
				face_linear.setVisibility(View.GONE);
				break;
			case R.id.faceby :
				typeface = OperateConstants.FACE_BY;
				face_linear.setVisibility(View.GONE);
				break;
			case R.id.facebygf :
				typeface = OperateConstants.FACE_BYGF;
				face_linear.setVisibility(View.GONE);
				break;
			default :
				break;
		}

	}

}
