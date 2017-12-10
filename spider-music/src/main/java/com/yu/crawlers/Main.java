package com.yu.crawlers;

import com.yu.crawlers.utils.SpringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
public class Main {
    private static ApplicationContext applicationContext;

    public synchronized static void run() {
        if (!ObjectUtils.allNotNull(Main.applicationContext)) {
            Main.applicationContext = new ClassPathXmlApplicationContext("spider-music-application.xml");
        }
    }
}
