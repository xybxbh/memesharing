package com.etc.emoji.task;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

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

public class AddCommentTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private String returnJSON;

    public AddCommentTask(Context context) {
        this.context = context;
    }
    @Override
    protected Boolean doInBackground(String... arg0) {
        String url = "http://139.199.158.77:8080/Emoji/AddCommentServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("commentinfo", arg0[0]));
        params.add(new BasicNameValuePair("userid", arg0[1]));
        params.add(new BasicNameValuePair("emojiid", arg0[2]));
        try {

            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                returnJSON = EntityUtils.toString(response.getEntity());
                System.out.println(returnJSON);

                if (returnJSON.equals("done")) {
                    return true;
                } else if (returnJSON.equals("failed")) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (JsonSyntaxException e) {
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

  





}
