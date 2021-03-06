package com.etc.emoji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.etc.emoji.task.RegisterTask;

public class RegisterActivity extends Activity {
    private EditText edtRegusername;
    private EditText edtRegpassword;
    String username;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegusername = (EditText) findViewById(R.id.edtRegusername);
        edtRegpassword= (EditText) findViewById(R.id.edtRegpassword);

    }
    public void register(View v)
    {
        username = edtRegusername.getText().toString();
        password = edtRegpassword.getText().toString();
        if((username+"1").equals("1") ||(password+"1").equals("1") )
        {
        	Toast.makeText(this, "用户名或密码不可为空", Toast.LENGTH_SHORT).show();
        }
        else{
        	  Intent intent = new Intent(RegisterActivity.this,
                      LoginActivity.class);
              new RegisterTask(this, intent).execute(username,password);
        }

      
    }
}
