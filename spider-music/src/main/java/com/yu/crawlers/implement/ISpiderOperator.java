package com.yu.crawlers.implement;

import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.yu.crawlers.bean.BaseObject;
import com.yu.crawlers.bean.SongSheet;

import java.util.Map;
import java.util.function.Consumer;

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
    Request[] getRequests();

    /**
     * 获取请求对象
     * @return
     */
    void setRequests(Request... request);

    /**
     * 获取请求错误回调
     * @return
     */
    Consumer<Request> getRequestErrorCallback();
    /**
     * 设置请求错误回调
     * @return
     */
    void setRequestErrorCallback(Consumer<Request> requestErrorCallback);

}
