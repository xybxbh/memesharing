package com.etc.emoji.task;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


public class ImageUpload extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... arg0) {
        HttpClient client = new DefaultHttpClient();

        HttpPost request = new HttpPost(arg0[0]);
        try {
        MultipartEntity entity = new MultipartEntity();

        File file = new File(arg0[1]);
        FileBody fileBody = new FileBody(file);
        entity.addPart("file", fileBody);

        StringBody info = new StringBody(arg0[2], Charset.forName("utf-8"));
        entity.addPart("info", info);

        StringBody userid = new StringBody(arg0[3], Charset.forName("utf-8"));
        entity.addPart("userid", userid);

        request.setEntity(entity);

        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){

            String responseText = EntityUtils.toString(response.getEntity());
            return responseText.equals("true");

        }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;





    }
}
