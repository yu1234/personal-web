package com.yu.crawlers.netease;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.Seimi;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.yu.crawlers.implement.ISpiderOperator;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
@Crawler(name = "SpiderMusic")
public class SpiderMusic extends BaseSeimiCrawler {
    private static Seimi s = new Seimi();
    private static ISpiderOperator spiderOperator;

    public String[] startUrls() {
        if (ObjectUtils.allNotNull(spiderOperator)&&ObjectUtils.anyNotNull(spiderOperator.getRequest())) {
            this.push(spiderOperator.getRequest());
        }
        return null;

    }

    public void start(Response response) {
        if (ObjectUtils.allNotNull(spiderOperator, response)) {
            spiderOperator.parse(response);
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

}
