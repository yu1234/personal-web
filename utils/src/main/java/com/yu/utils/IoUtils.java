package com.yu.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
}
