package com.yu.crawlers.netease;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.Seimi;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
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
        if (ObjectUtils.allNotNull(spiderOperator)) {
            return getStartUrls(spiderOperator.getUrl(), spiderOperator.getParams());
        } else {
            return new String[0];
        }


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
        SpiderMusic.spiderOperator=spiderOperator;
        s.goRun( "SpiderMusic");

    }


    /**
     * 参数拼接
     *
     * @return
     */
    private String spliceParams(Map<String, String> params) {
        StringBuffer sb = new StringBuffer("");
        if (ObjectUtils.allNotNull(params)) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (ObjectUtils.allNotNull(key, value)) {
                    sb.append(key + "=" + value + "&");
                }
            }
        }
        return sb.toString();
    }


    /**
     * 设置爬取路径
     *
     * @param params
     */
    private String[] getStartUrls(String url, Map<String, String>... params) {
        String[] r = new String[0];
        if (ObjectUtils.allNotNull(url)) {
            if (ObjectUtils.allNotNull(params) && params.length > 0) {
                for (Map<String, String> param : params) {
                    String paramStr = this.spliceParams(param);
                    r = new String[]{url + "?" + paramStr};
                }
            } else {
                r = new String[]{url};
            }
        }
        return r;

    }
}
