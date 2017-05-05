package com.etc.emoji.entity;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/4/12.
 */

public class Material implements Serializable {
    private int materialid;
    private User user;
    private String materialphoto;
    private String materialinfo;
    private String materialuploadtime;


    public int getMaterialid() {
        return materialid;
    }

    public void setMaterialid(int materialid) {
        this.materialid = materialid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMaterialphoto() {
        return materialphoto;
    }

    public void setMaterialphoto(String materialphoto) {
        this.materialphoto = materialphoto;
    }

    public String getMaterialinfo() {
        return materialinfo;
    }

    public void setMaterialinfo(String materialinfo) {
        this.materialinfo = materialinfo;
    }

    public String getMaterialuploadtime() {
        return materialuploadtime;
    }

    public void setMaterialuploadtime(String materialuploadtime) {
        this.materialuploadtime = materialuploadtime;
    }
}
