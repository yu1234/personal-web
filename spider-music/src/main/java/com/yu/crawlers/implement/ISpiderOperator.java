package com.yu.crawlers.implement;

import cn.wanghaomiao.seimi.struct.Response;

import java.util.Map;

/**
 * Created by yuliu on 2017/12/5 0005.
 */
public interface ISpiderOperator {

    //解析回调
    void parse(Response response);

    //获取爬虫url
    String getUrl();

    //获取参数
    Map<String, String> getParams();

    //设置url
    void setUrl(String url);

    //设置参数
    void setParams(Map<String, String>  params);
}
