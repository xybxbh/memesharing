package com.etc.emoji.task;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.etc.emoji.EmojiMainPageActivity;
import com.etc.emoji.adapter.SearchEmojiSimpleAdapter;
import com.etc.emoji.entity.Emoji;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ShowEmojiSearchTask extends AsyncTask <String, Void, List<Emoji>>{

    private ListView listResult;
    private Context context;
    String emojiListGson;

    public ShowEmojiSearchTask(Context context,ListView listResult) {
        this.listResult = listResult;
        this.context = context;
    }
    @Override
    protected List<Emoji> doInBackground(String... arg0) {
        String url = "http://139.199.158.77:8080/Emoji/UserSearchEmojiServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tagname", arg0[0]));

        List<Emoji> list = null;
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                emojiListGson = EntityUtils.toString(response.getEntity());

                Gson gson = new Gson();

                System.out.println(emojiListGson);

                list = gson.fromJson(emojiListGson, new TypeToken<List<Emoji>>() {
                }.getType());
                return list;
            } else {
                return null;
            }} catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }



    protected void onPostExecute(List<Emoji> result) {
        final List<Emoji>  list = result;
        if(result!=null){
           
            SearchEmojiSimpleAdapter adapter = new SearchEmojiSimpleAdapter(context,result);
            listResult.setAdapter(adapter);
            listResult.setVisibility(View.VISIBLE);
            listResult
                    .setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int arg2, long arg3) {
                            Intent intent = new Intent(context,
                                    EmojiMainPageActivity.class);
                            intent.putExtra("emoji", list.get(arg2));
                            context.startActivity(intent);

                        }
                    });
            }
        else
        {
            Toast.makeText(context, "无搜索结果", Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(result);

    }



}
