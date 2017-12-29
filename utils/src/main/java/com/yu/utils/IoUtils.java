package com.yu.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuliu on 2017/11/26 0026.
 */
public class IoUtils {
    /**
     * 把字符串写入文件夹
     *
     * @param filePath 文件路径
     * @param content  内容
     */
    public static boolean WriteStringToFile(String filePath, String content) {
        boolean flag = false;
        try {
            File file = new File(filePath);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
            flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean existsUrl(String URLName) {
        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接

            HttpURLConnection con = (HttpURLConnection) new URL(URLName)

                    .openConnection();

            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }
}
