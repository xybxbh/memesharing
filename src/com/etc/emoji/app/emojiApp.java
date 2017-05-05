package com.etc.emoji.app;


import android.app.Application;
import android.net.Uri;

import com.etc.emoji.entity.User;

import org.xutils.x;

public class emojiApp extends Application {
    private User user;
    private Uri uri;
   

    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);//是否输出Debug日志
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
	



}
