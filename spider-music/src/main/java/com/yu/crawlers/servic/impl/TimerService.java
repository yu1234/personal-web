package com.yu.crawlers.servic.impl;


import cn.wanghaomiao.seimi.struct.Request;
import com.alibaba.fastjson.JSON;
import com.yu.crawlers.bean.SongSheet;
import com.yu.crawlers.implement.IParseCallback;
import com.yu.crawlers.implement.IRequestErrorCallback;
import com.yu.crawlers.implement.SongSheetType;
import com.yu.crawlers.netease.SpiderMusic;
import com.yu.crawlers.netease.parse.SongSheetParse;
import com.yu.crawlers.servic.ITimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yuliu on 2017/12/11 0011.
 */
@Service
public class TimerService implements ITimerService {
    @Autowired
    SpiderMusic spiderMusic;
    //网易排行榜id列表
    private final static String[] topList = new String[]{
            "19723756", "3779629", "2884035", "3778678", "1978921795", "991319590", "60131", "71385702", "10520166", "3812895", "71384707", "180106",
            "60198", "27135204", "11641012", "120001", "2006508653", "21845217", "112463", "112504", "64016", "10169002", "1899724"
    };

    /**
     * 更新网易排行榜
     */
    public void updateTopList() {
        SongSheetParse songSheetParse = new SongSheetParse(SongSheetType.TOP_LIST, topList);

        songSheetParse.setCallback(songSheet -> System.out.println("---------------------更新网易排行榜:" + songSheet.getName() + "  " + new Date().toLocaleString() + "-----------------------------"));

        songSheetParse.setRequestErrorCallback(request -> System.out.println("---------------------更新失败" + JSON.toJSONString(request.getParams()) + "  " + new Date().toLocaleString() + "-----------------------------"));
        spiderMusic.run(songSheetParse);
    }
}
