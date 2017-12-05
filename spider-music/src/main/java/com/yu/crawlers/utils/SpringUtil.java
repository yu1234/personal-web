package com.yu.crawlers.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by yuliu on 2017/11/30 0030.
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    public static ApplicationContext applicationContext = null;

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的id获取相应类型的对象
     *
     * @param name bean的id
     * @return Object类型的对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);

    }

    /**
     * 根据bean的名称获取相应类型的对象，使用泛型，获得结果后，不需要强制转换为相应的类型
     *
     * @param clazz bean的类型，使用泛型
     * @return T类型的对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }
}
