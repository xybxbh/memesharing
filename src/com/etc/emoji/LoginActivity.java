package com.etc.emoji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.etc.emoji.app.emojiApp;
import com.etc.emoji.task.UserLoginTask;

public class LoginActivity extends Activity {

    private EditText edtLoginusername;
    private EditText edtPassword;
    private String username;
    private String password;
    private CheckBox ckbLoginSave;
    private emojiApp myApp;
    private SharedPreferences sp;
    private Boolean ischecked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginusername = (EditText) findViewById(R.id.edtLoginusername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        ckbLoginSave = (CheckBox) findViewById(R.id.ckbLoginSave);

        myApp = (emojiApp) getApplication();
        sp = getSharedPreferences("UserInfo",MODE_PRIVATE);


    }
    public void login(View v) {

        username = edtLoginusername.getText().toString();
        password = edtPassword.getText().toString();
        ischecked = ckbLoginSave.isChecked();
       // Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, password, Toast.LENGTH_SHORT).show();

        
        if((username+"1").equals("1") ||(password+"1").equals("1") )
        {
        	Toast.makeText(this, "用户名或密码不可为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
        	Intent intent = new Intent(LoginActivity.this, MainActivity.class);
       	 new UserLoginTask(ischecked,ckbLoginSave,myApp,sp,this,intent).execute(username,password);
        }

       
        
    }
    public void register(View v)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }








}
