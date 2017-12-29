package com.yu.spider.music.netease.webmagic;

import com.yu.spider.music.implement.BasePageProcessor;
import org.apache.commons.lang3.ObjectUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Arrays;
import java.util.List;

public class PageProcessorMain {

    public static void run(BasePageProcessor pageProcessor) {
       PageProcessorMain.run(pageProcessor,5);
    }
    public static void run(BasePageProcessor pageProcessor,int thread) {
        if (ObjectUtils.allNotNull(pageProcessor)) {
            Spider spider = Spider.create(pageProcessor).thread(thread);
            if (pageProcessor.getRequests() != null && pageProcessor.getRequests().length > 0) {
                spider.addRequest(pageProcessor.getRequests());
            }
            if (pageProcessor.getPipelines() != null && pageProcessor.getPipelines().length > 0) {
                List<Pipeline> pipelines = Arrays.asList(pageProcessor.getPipelines());
                spider.setPipelines(pipelines);
            }
            spider.start();
        }
    }
}
