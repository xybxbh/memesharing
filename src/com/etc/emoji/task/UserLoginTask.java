package com.etc.emoji.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.Toast;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.entity.User;
import com.google.gson.Gson;
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

public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

    private CheckBox ckbLoginSave;
    private Boolean ischecked;
    private emojiApp emojiApp;
    private SharedPreferences sp;
    private Context context;
    private Intent intent;
    private String userJSON;
    User user ;

    public UserLoginTask(Boolean ischecked, CheckBox ckbLoginSave, emojiApp emojiApp,
                         SharedPreferences sp, Context LoginContext, Intent intent) {
        this.ischecked = ischecked;
        this.ckbLoginSave = ckbLoginSave;
        this.emojiApp = emojiApp;
        this.sp = sp;
        this.context = LoginContext;
        this.intent = intent;
    }
    @Override
    protected Boolean doInBackground(String... arg0) {
        String url = "http://139.199.158.77:8080/Emoji/UserLogiinServlet";
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

                Gson gson = new Gson();
                user = gson.fromJson(userJSON, User.class);
               

                emojiApp.setUser(user);

                if (ckbLoginSave != null) {
                    if (ischecked) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", user.getUsername());
                        editor.putString("password", user.getPassword());
                        editor.commit();
                    }
                }
                return true;
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
        if (result == true&&user.getUsername()!=null&&user.getPassword()!=null) {
        	
        		 context.startActivity(intent);
                 Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                 
     

           
        } else {
            Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);

    }
}
