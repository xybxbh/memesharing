package com.etc.emoji.task;


import android.content.Context;
import android.content.Intent;
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

public class RegisterTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private Intent intent;
    private String userJSON;

    public RegisterTask(Context WelcomeContext, Intent intent) {
        this.context = WelcomeContext;
        this.intent = intent;
    }


    @Override
    protected Boolean doInBackground(String... arg0) {
        String url = "http://139.199.158.77:8080/Emoji/UserRegisterServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", arg0[0]));
        params.add(new BasicNameValuePair("password", arg0[1]));

        try {

            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                userJSON = EntityUtils.toString(response.getEntity());
                System.out.println(userJSON);

                if (userJSON.equals("注册成功")) {
                    return true;
                } else if (userJSON.equals("用户已存在，请重新输入")) {
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

    @Override
    protected void onPostExecute(Boolean result) {
        if (result == true) {
           // Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();


            Toast.makeText(context, userJSON, Toast.LENGTH_SHORT).show();

        } else {
            if (userJSON == null) {
                Toast.makeText(context, "无法联系上服务器", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, userJSON, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "falied", Toast.LENGTH_SHORT).show();
            }

        }
        context.startActivity(intent);
        super.onPostExecute(result);

    }
}
