package com.etc.emoji.entity;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/4/12.
 */

public class Emoji implements Serializable {
    private int emojiid;
    private User user;
    private Material material;
    private String emojiphoto;
    private String emojiinfo;
    private String emojiuploadtime;


    public int getEmojiid() {
        return emojiid;
    }

    public void setEmojiid(int emojiid) {
        this.emojiid = emojiid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getEmojiphoto() {
        return emojiphoto;
    }

    public void setEmojiphoto(String emojiphoto) {
        this.emojiphoto = emojiphoto;
    }

    public String getEmojiinfo() {
        return emojiinfo;
    }

    public void setEmojiinfo(String emojiinfo) {
        this.emojiinfo = emojiinfo;
    }

    public String getEmojiuploadtime() {
        return emojiuploadtime;
    }

    public void setEmojiuploadtime(String emojiuploadtime) {
        this.emojiuploadtime = emojiuploadtime;
    }
}
