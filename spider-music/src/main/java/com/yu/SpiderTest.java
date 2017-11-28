package com.yu;

import com.yu.crawlers.Main;
import com.yu.crawlers.implement.SearchType;
import com.yu.crawlers.implement.SpiderInfoCallback;
import com.yu.crawlers.music163.SpiderMusic;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuliu on 2017/11/28 0028.
 */
public class SpiderTest implements SpiderInfoCallback {

    public static void main(String[] args) {
        SpiderTest test = new SpiderTest();
        test.mainTest();
    }

    public void mainTest() {
        Main.instance();
        SpiderMusic spiderMusic = Main.getBean(SpiderMusic.class);
        if (ObjectUtils.allNotNull(spiderMusic)) {
            Map map = new HashMap();
            map.put("id", "1980616764");
            spiderMusic.run(SearchType.PLAY_LIST_INFO, map, this);
        }
    }

    public void successCallback(Object o) {

    }
}
