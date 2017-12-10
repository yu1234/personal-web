package com.yu.crawlers.bean;

/**
 * Created by yuliu on 2017/12/10 0010.
 */
public class Album extends BaseObject {
    private String name;
    private String picUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
