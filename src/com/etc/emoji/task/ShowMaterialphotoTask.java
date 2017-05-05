package com.etc.emoji.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.etc.emoji.MaterialMainPageActivity;
import com.etc.emoji.adapter.GridViewMaterialAdapter;
import com.etc.emoji.app.emojiApp;
import com.etc.emoji.entity.Material;
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



public class ShowMaterialphotoTask extends AsyncTask<String, Void, List<Material>> {
    private GridView gridMaterial;
    private Context context;
    String materialListGson;
  

    public ShowMaterialphotoTask(Context context, GridView gridMaterial) {
        this.gridMaterial = gridMaterial;
        this.context = context;
       
    }
    @Override
    protected List<Material> doInBackground(String... arg0) {
        String url = "http://10.0.2.2:8080/Emoji/ShowAllMaterialServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", arg0[0]));
        
        List<Material> list = null;
        try {
        	 request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            materialListGson = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();

            System.out.println(materialListGson);

            list = gson.fromJson(materialListGson, new TypeToken<List<Material>>() {
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
    protected void onPostExecute(List<Material> result) {
        final List<Material>  list = result;
        if(result!=null){
        	

            GridViewMaterialAdapter adapter = new GridViewMaterialAdapter(context,result);
            gridMaterial.setAdapter(adapter);
            gridMaterial
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int arg2, long arg3) {
                                 Intent intent = new Intent(context,
                                         MaterialMainPageActivity.class);
                                intent.putExtra("material", list.get(arg2));

                                context.startActivity(intent);

                        }
                    });
            gridMaterial.setVisibility(View.VISIBLE);}
        else
        {
            Toast.makeText(context,"WRONG",Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(result);



    }

}
