package com.yu.crawlers.implement;

import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.yu.crawlers.bean.BaseObject;
import com.yu.crawlers.bean.SongSheet;

import java.util.Map;

/**
 * Created by yuliu on 2017/12/5 0005.
 */
public interface ISpiderOperator {

    /**
     * 解析回调
     *
     * @param response
     */
    void parse(Response response);

    /**
     * 设置请求对象
     * @return
     */
    Request getRequest();

    /**
     * 获取请求对象
     * @return
     */
    void setRequest(Request request);

}
