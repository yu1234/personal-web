package com.yu.crawlers.bean;

import org.springframework.data.annotation.Id;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
public class BaseObject {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
