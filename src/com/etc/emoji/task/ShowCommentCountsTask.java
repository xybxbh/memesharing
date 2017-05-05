package com.etc.emoji.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

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


public class ShowCommentCountsTask extends AsyncTask<String, Void, Boolean > {
    private TextView txtEmojiCommentCounts;
    private Context context;
    String countGson;
    public ShowCommentCountsTask(Context context, TextView txtEmojiCommentCounts) {
        this.txtEmojiCommentCounts = txtEmojiCommentCounts;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... arg0) {
        String url = "http://10.0.2.2:8080/Emoji/ShowCommentCountsServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("emojiid", arg0[0]));
        int count=0;

        try {
        request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            countGson = EntityUtils.toString(response.getEntity());
            System.out.println(countGson);
           
            
           // Gson gson = new Gson();
           // count = gson.fromJson(countGson, new TypeToken<Integer>() {
          //  }.getType());
            

            return true;
        } else {
            return false;
        }} catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

        }
    @Override
    protected void onPostExecute(Boolean result) {
        if (result == true) {

        	 txtEmojiCommentCounts.setText("评论:"+"("+countGson+")");
           
        } else {
            Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);

    }
  

    }

