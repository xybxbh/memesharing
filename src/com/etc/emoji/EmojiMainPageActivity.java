package com.etc.emoji;

import org.xutils.x;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etc.emoji.entity.Emoji;
import com.etc.emoji.task.ShowCommentCountsTask;
import com.etc.emoji.task.ShowEmojiCommentsTask;
import com.etc.emoji.task.ShowEmojiphotoTask;

public class EmojiMainPageActivity extends Activity implements OnRefreshListener{


    private ImageView imgEmojiMainPage;
    private TextView txtEmojiMainPageInfo;
    private TextView txtEmojiCommentCounts;
    private ListView listEmojiComments;
    private String urlString = "http://139.199.158.77:8080/Emoji/image/emoji/";
    private int emojiid;
    Emoji emoji;
    private SwipeRefreshLayout swipe;
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_main_page);

        imgEmojiMainPage = (ImageView)findViewById(R.id.imgEmojiMainPage);
        txtEmojiMainPageInfo = (TextView)findViewById(R.id.txtEmojiMainPageInfo);
        txtEmojiCommentCounts = (TextView)findViewById(R.id.txtEmojiCommentCounts);
        listEmojiComments = (ListView)findViewById(R.id.listEmojiComments);

        //获取选中的emoji对象，进入它的主页。
        Intent intent = this.getIntent();
         emoji = (Emoji)intent.getSerializableExtra("emoji");
        emojiid = emoji.getEmojiid();
        
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe_container_comment);
		swipe.setOnRefreshListener(this);
		swipe.setColorScheme(R.color.white, R.color.red, R.color.white, R.color.red);

        txtEmojiMainPageInfo.setText(emoji.getEmojiinfo());
        String picname = emoji.getEmojiphoto();
        x.image().bind(imgEmojiMainPage, urlString + picname);

        new ShowCommentCountsTask(this,txtEmojiCommentCounts).execute(emoji.getEmojiid()+"");
              
        new ShowEmojiCommentsTask(this,listEmojiComments).execute(emoji.getEmojiid()+"");

       
     
    }

    public void back(View V)
    {
       // Intent intent = new Intent(EmojiMainPageActivity.this, MainActivity.class);
      //  startActivity(intent);
        finish();
    }
    public void addcomment(View V)
    {
    	String id = emojiid +"";
    
        Intent intent = new Intent(EmojiMainPageActivity.this, AddCommentActivity.class);
        intent.putExtra("emojiid",id);
        startActivity(intent);

    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		swipe.setRefreshing(false);
		 new ShowCommentCountsTask(this,txtEmojiCommentCounts).execute(emoji.getEmojiid()+"");
		new ShowEmojiCommentsTask(this,listEmojiComments).execute(emoji.getEmojiid()+"");
		
		
	}
}
