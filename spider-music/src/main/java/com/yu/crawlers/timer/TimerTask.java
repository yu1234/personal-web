package com.yu.crawlers.timer;

import com.yu.crawlers.servic.ITimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yuliu on 2017/12/11 0011.
 */
@Component
public class TimerTask {
    @Autowired
    private ITimerService timerService;

    /**
     * 每天凌晨2点更新网易排行榜
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void  updateTopListTask(){
        this.timerService.updateTopList();
    }
}
