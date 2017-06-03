package com.etc.emoji.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.etc.emoji.adapter.ShowCommentsAdapter;
import com.etc.emoji.entity.Comment;
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


public class ShowEmojiCommentsTask extends AsyncTask<String, Void, List<Comment>> {

    private ListView listEmojiComments;
    private Context context;
    String commentListGson;
    public ShowEmojiCommentsTask(Context context,ListView listEmojiComments) {
        this.listEmojiComments = listEmojiComments;
        this.context = context;
    }

    @Override
    protected List<Comment> doInBackground(String... arg0) {
        String url = "http://139.199.158.77:8080/Emoji/ShowEmojiCommentsServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("emojiid", arg0[0]));

        List<Comment> list = null;
        try {
            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                commentListGson = EntityUtils.toString(response.getEntity());

                Gson gson = new Gson();

                System.out.println(commentListGson);

                list = gson.fromJson(commentListGson, new TypeToken<List<Comment>>() {
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

    protected void onPostExecute(List<Comment> result) {
        final List<Comment>  list = result;
        if(result!=null){
            //ImageLoadTask imageLoadTask = new ImageLoadTask();
            ShowCommentsAdapter adapter = new ShowCommentsAdapter(context,result);
            listEmojiComments.setAdapter(adapter);

            listEmojiComments.setVisibility(View.VISIBLE);}
        else
        {
            Toast.makeText(context, "WRONG", Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(result);

    }
}
