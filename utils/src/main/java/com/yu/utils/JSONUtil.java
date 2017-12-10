package com.yu.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuliu on 2017/12/6 0006.
 */
public class JSONUtil {
    private JSONObject jsonObject;

    public static JSONUtil parseObject(String text) {
        JSONUtil jsonUtil = new JSONUtil();
        try {
            JSONObject jsonObject = JSON.parseObject(text);
            jsonUtil.setJsonObject(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonUtil;
    }

    public static JSONUtil parseObject(JSONObject jsonObject) {
        JSONUtil jsonUtil = new JSONUtil();
        jsonUtil.setJsonObject(jsonObject);
        return jsonUtil;
    }

    /**
     * 根据key获取对象
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        return this.get(key, Object.class);
    }

    public JSONArray getJSONArray(String key) {
        return this.get(key, JSONArray.class);
    }

    /**
     * 根据key获取值
     */
    public <T> T get(String key, Class<T> clazz) {
        T value = null;
        try {
            if (ObjectUtils.allNotNull(jsonObject, key)) {
                String[] keys = key.split("\\.");
                if (!ObjectUtils.allNotNull(keys) || keys.length <= 0) {
                    keys = new String[]{key};
                }
                JSONObject jsonObjectTemp = jsonObject;
                for (int i = 0, len = keys.length; i < len; i++) {
                    if (ObjectUtils.allNotNull(jsonObjectTemp)) {
                        String k = keys[i];
                        if (i == (len - 1)) {
                            if (k.endsWith("]")) {
                                String temp = k;
                                k = k.substring(0, k.length() - 2);
                                String[] aKeys = k.split("\\[");
                                if (ObjectUtils.allNotNull(aKeys) && aKeys.length > 1 && NumberUtils.isDigits(aKeys[1])) {
                                    int index = NumberUtils.toInt(aKeys[1]);
                                    JSONArray jsonArray = jsonObjectTemp.getJSONArray(aKeys[0]);
                                    if (index < jsonArray.size()) {
                                        value = jsonArray.getJSONObject(index).getObject(k, clazz);
                                    } else {
                                        value = jsonObjectTemp.getObject(k, clazz);
                                    }
                                } else {
                                    value = jsonObjectTemp.getObject(k, clazz);
                                }
                            } else {
                                value = jsonObjectTemp.getObject(k, clazz);
                            }
                        } else {
                            if (k.endsWith("]")) {
                                String temp = k;
                                k = k.substring(0, k.length() - 1);
                                String[] aKeys = k.split("\\[");
                                if (ObjectUtils.allNotNull(aKeys) && aKeys.length > 1 && NumberUtils.isDigits(aKeys[1])) {
                                    int index = NumberUtils.toInt(aKeys[1]);
                                    JSONArray jsonArray = jsonObjectTemp.getJSONArray(aKeys[0]);
                                    if (index < jsonArray.size()) {
                                        jsonObjectTemp = jsonArray.getJSONObject(index);
                                    } else {
                                        jsonObjectTemp = jsonObjectTemp.getJSONObject(temp);
                                    }
                                } else {
                                    jsonObjectTemp = jsonObjectTemp.getJSONObject(temp);
                                }
                            } else {
                                jsonObjectTemp = jsonObjectTemp.getJSONObject(k);
                            }

                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return value;
    }


    /**
     * 批量获取值
     *
     * @return
     */
    public Map<String, Object> getBatchValue(String... keys) {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        if (ObjectUtils.allNotNull(keys) && keys.length > 0) {
            for (String key : keys) {
                Object o = this.getObject(key);
                valueMap.put(key, o);
            }
        }
        return valueMap;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
