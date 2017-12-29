package com.yu.spider.music.implement;



import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public interface BasePageProcessor extends PageProcessor {
    /**
     * 设置请求对象
     * @return
     */
    Request[] getRequests();

    /**
     * 设置通道
     * @return
     */
    Pipeline[] getPipelines();

}
