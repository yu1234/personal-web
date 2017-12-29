package com.yu.spider.music.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Data
public class BaseObject {
    public final static String ATTR_ID="id";
    @Id
    private String id;

}
