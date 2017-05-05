package com.etc.emoji;

import org.xutils.x;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.etc.emoji.entity.Material;
import com.etc.emoji.task.ShowEmojiphotoTask;
import com.etc.emoji.task.ShowEmojisofMaterialTask;

public class MaterialMainPageActivity extends Activity implements OnRefreshListener {
    private GridView gridMaterialMainpage;
    private TextView txtMaterialmainpageInfo;
    private ImageView imgMaterialmainPage;
    private String urlString = "http://10.0.2.2:8080/Emoji/image/material/";
    private SwipeRefreshLayout swipe;
    private Material material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_main_page);
        
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_container_me);
		swipe.setOnRefreshListener(this);
		swipe.setColorScheme(R.color.white, R.color.red, R.color.white, R.color.red);

        gridMaterialMainpage = (GridView)findViewById(R.id.gridMaterialMainpage);
        txtMaterialmainpageInfo = (TextView)findViewById(R.id.txtMaterialmainpageInfo);
        imgMaterialmainPage = (ImageView)findViewById(R.id.imgMaterialmainPage);
        //获取选中的emoji对象，进入它的主页。
        Intent intent = this.getIntent();
        material = (Material)intent.getSerializableExtra("material");
        txtMaterialmainpageInfo.setText(material.getMaterialinfo());
        String picname = material.getMaterialphoto();
        x.image().bind(imgMaterialmainPage, urlString + picname);

        new ShowEmojisofMaterialTask(this,gridMaterialMainpage).execute(material.getMaterialid()+"");


    }
    public void back(View V)
    {
       // Intent intent = new Intent(MaterialMainPageActivity.this, MainActivity.class);
       // startActivity(intent);
    	finish();
    }
    public void make(View V)
    {
    	Intent intent = new Intent(MaterialMainPageActivity.this,AddTextActivity.class);
    	String camera_path = urlString + material.getMaterialphoto();
        intent.putExtra("camera_path", camera_path);
        intent.putExtra("materialid", material.getMaterialid()+"");
        intent.putExtra("material", material);
        startActivity(intent);
    }
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		swipe.setRefreshing(false);
		new ShowEmojisofMaterialTask(this,gridMaterialMainpage).execute(material.getMaterialid()+"");
	}






}
