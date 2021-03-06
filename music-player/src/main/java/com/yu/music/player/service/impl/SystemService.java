package com.yu.music.player.service.impl;

import com.yu.music.player.service.ISystemService;
import com.yu.utils.StrUtils;
import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import java.math.BigInteger;

public class SystemService implements ISystemService {

    /**
     * 网易云手机登录
     * @param username
     * @param password
     * @throws Exception
     */
   public void neteaseLoginPhone(String username,String password) throws Exception {
       password = StrUtils.md5(password);
       // 私钥，随机16位字符串（自己可改）
       String secKey = "cd859f54539b24b7";
       String text = "{\"phone\": \"" + username + "\", \"rememberLogin\": \"true\", \"password\": \"" + password
               + "\"}";
       String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
       String nonce = "0CoJUm6Qyw8W8jud";
       String pubKey = "010001";
       // 2次AES加密，得到params
       String params = StrUtils.encrypt(StrUtils.encrypt(text, nonce), secKey);
       StringBuffer stringBuffer = new StringBuffer(secKey);
       // 逆置私钥
       secKey = stringBuffer.reverse().toString();
       String hex = Hex.encodeHexString(secKey.getBytes());
       BigInteger bigInteger1 = new BigInteger(hex, 16);
       BigInteger bigInteger2 = new BigInteger(pubKey, 16);
       BigInteger bigInteger3 = new BigInteger(modulus, 16);
       // RSA加密计算
       BigInteger bigInteger4 = bigInteger1.pow(bigInteger2.intValue()).remainder(bigInteger3);
       String encSecKey = Hex.encodeHexString(bigInteger4.toByteArray());
       // 字符填充
       encSecKey = StrUtils.zfill(encSecKey, 256);
       // 登录请求
       org.jsoup.nodes.Document document = Jsoup.connect("https://music.163.com/weapi/login/cellphone").cookie("appver", "1.5.0.75771")
               .header("Referer", "http://music.163.com/").data("params", params).data("encSecKey", encSecKey)
               .ignoreContentType(true).post();
       System.out.println("登录结果：" + document.text());
   }
}
