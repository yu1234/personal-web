package com.yu.utils;

import org.apache.commons.lang3.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
public class StrUtils {
    /**
     * 继续并获取路径参数
     *
     * @param matchUrl 解析路径
     * @param key      获取参数的key值
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

    /**
     * object to String
     */
    public static String objectToString(Object o) {
        String value="";
        if (ObjectUtils.allNotNull(o)) {
            value = o.toString();
        }
        return value;
    }

    public static String md5(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(pwd.getBytes());
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    //AES加密
    public static String encrypt(String text, String secKey) throws Exception {
        byte[] raw = secKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    //字符填充
    public static String zfill(String result, int n) {
        if (result.length() >= n) {
            result = result.substring(result.length() - n, result.length());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n; i > result.length(); i--) {
                stringBuilder.append("0");
            }
            stringBuilder.append(result);
            result = stringBuilder.toString();
        }
        return result;
    }
}
