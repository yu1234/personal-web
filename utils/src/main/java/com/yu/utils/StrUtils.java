package com.yu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
public class StrUtils {
    /**
     * 继续并获取路径参数
     * @param matchUrl 解析路径
     * @param key 获取参数的key值
     * @return
     */
    public static String parseUrlParam(String matchUrl, String key) {
        String r = null;
        if (matchUrl != null && key != null) {
            String reg = "([^?&=]+)((?:=([^?&=]*))*)";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(matchUrl);
            while (m.find()) {
                if (key.equals(m.group(1))) {
                    r = m.group(3);
                }

            }
        }
        return r;
    }
}
