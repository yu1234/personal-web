package com.yu.crawlers;

import com.yu.crawlers.utils.SpringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
public class Main {


    private static void run() {
        if (!ObjectUtils.allNotNull(SpringUtil.applicationContext)) {
            new ClassPathXmlApplicationContext("application.xml");
        }
    }
}
