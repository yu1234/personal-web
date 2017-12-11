package com.yu.crawlers.netease;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.Seimi;
import cn.wanghaomiao.seimi.core.SeimiQueue;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.alibaba.fastjson.JSON;
import com.yu.crawlers.implement.ISpiderOperator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
@Component
@Crawler(name = "SpiderMusic")
public class SpiderMusic extends BaseSeimiCrawler {
    private static Seimi s = new Seimi();
    private static ISpiderOperator spiderOperator;

    public String[] startUrls() {
        if (ObjectUtils.allNotNull(spiderOperator) && ObjectUtils.anyNotNull(spiderOperator.getRequests()) && spiderOperator.getRequests().length > 0) {
            for (Request request : spiderOperator.getRequests()) {
                this.push(request);
            }
        }
        return null;

    }

    public synchronized void start(Response response) {
        if (ObjectUtils.allNotNull(spiderOperator)) {
            spiderOperator.parse(response);
        }
        if(this.queue.len("SpiderMusic")<=0){
            System.out.println("============================================SpiderMusic request end======================================================");
        }
    }


    /**
     * 开始爬虫
     *
     * @param
     */
    public void run(ISpiderOperator spiderOperator) {
        SpiderMusic.spiderOperator = spiderOperator;
        s.goRun(false, "SpiderMusic");

    }

    @Override
    public void handleErrorRequest(Request request) {
        super.handleErrorRequest(request);
        if(ObjectUtils.allNotNull(spiderOperator)&&ObjectUtils.allNotNull(spiderOperator.getRequestErrorCallback())){
            spiderOperator.getRequestErrorCallback().errorCallback(request);
        }
    }
}
