package com.yu;

import com.yu.crawlers.Main;
import com.yu.crawlers.implement.SearchType;
import com.yu.crawlers.implement.SpiderInfoCallback;
import com.yu.crawlers.music163.SpiderMusic;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.List;
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
            map.put("ids", "[33544055]");
            map.put("br", "320000");
            spiderMusic.run(SearchType.SONG, this, map);
        }
    }

    public void successCallback(Object o) {

    }
}
