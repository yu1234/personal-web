package com.yu.crawlers;

import com.yu.crawlers.implement.SearchType;
import com.yu.crawlers.music163.SpiderMusic;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
public class Main  {
    private static ApplicationContext context;
    private static boolean isReload = false;

    private static void run() {
        if (!ObjectUtils.allNotNull(context) || isReload) {
            context = new ClassPathXmlApplicationContext("application.xml");
        }

    }

    public static void instance() {
        isReload = false;
        run();
    }

    public static void reload() {
        isReload = true;
        run();
    }

    /**
     * 根据bean的名称获取相应类型的对象，使用泛型，获得结果后，不需要强制转换为相应的类型
     *
     * @param clazz bean的类型，使用泛型
     * @return T类型的对象
     */
    public static <T> T getBean(Class<T> clazz) {
        if (ObjectUtils.allNotNull(context)) {
            T bean = context.getBean(clazz);
            return bean;
        }
        return null;
    }

    /**
     * 根据bean的id获取相应类型的对象
     *
     * @param beanId bean的id
     * @return Object类型的对象
     */
    public static Object getBean(String beanId) {
        if (ObjectUtils.allNotNull(context)) {
            Object bean = context.getBean(beanId);
            return bean;
        }
        return null;
    }



}
