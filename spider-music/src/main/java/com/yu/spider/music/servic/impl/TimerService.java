package com.yu.spider.music.servic.impl;


import com.yu.spider.music.implement.SongSheetType;
import com.yu.spider.music.netease.webmagic.PageProcessorMain;
import com.yu.spider.music.netease.webmagic.pageprocessor.SongSheetPageProcessor;
import com.yu.spider.music.servic.ITimerService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yuliu on 2017/12/11 0011.
 */
@Service
public class TimerService implements ITimerService {

    //网易排行榜id列表
    private final static String[] topList = new String[]{
            "19723756", "3779629", "2884035", "3778678", "1978921795", "991319590", "60131", "71385702", "10520166", "3812895", "71384707", "180106",
            "60198", "27135204", "11641012", "120001", "2006508653", "21845217", "112463", "112504", "64016", "10169002", "1899724"
    };

    /**
     * 更新网易排行榜
     */
    public void updateTopList() {
        SongSheetPageProcessor songSheetPageProcessor = new SongSheetPageProcessor(SongSheetType.TOP_LIST, topList);


        songSheetPageProcessor.setCallback(songSheet -> System.out.println("---------------------更新网易排行榜:" + songSheet.getName() + "  " + new Date().toLocaleString() + "-----------------------------"));

        PageProcessorMain.run(songSheetPageProcessor, 1);
    }
}
