package com.etc.emoji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.etc.emoji.task.ShowEmojiSearchTask;
import com.etc.emoji.task.ShowMaterialSearchTask;

public class SearchActivity extends Activity {
    
    private EditText edtTagname;
    private Spinner spnSelect;
    private ListView listResult;
    String selected;
    String tagname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       
        edtTagname = (EditText)findViewById(R.id.edtTagname);
        spnSelect = (Spinner)findViewById(R.id.spnSelect);
        listResult = (ListView)findViewById(R.id.listResult);

    }

    public void back(View V)
    {

    	  Intent intent = new Intent(SearchActivity.this, MainActivity.class);
          startActivity(intent);
    	
      

    }
    public void search(View V)
    {
        tagname = edtTagname.getText().toString();
        selected = spnSelect.getSelectedItem().toString();

         if(selected.equals("表情包"))
        {
            new ShowEmojiSearchTask(this,listResult).execute(tagname);
        }
         else if(selected.equals("素材"))
         {
             new ShowMaterialSearchTask(this,listResult).execute(tagname);

         }

    }

}
