package com.yu.utils;

import com.yu.utils.bean.LrcData;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class LyricAnalyze {
    private static LyricAnalyze lyricAnalyze=new LyricAnalyze();
    /**
     * [ar:艺人名] [ti:曲名] [al:专辑名] [by:编者（指编辑LRC歌词的人）] [offset:时间补偿值]
     * 其单位是毫秒，正值表示整体提前，负值相反。这是用于总体调整显示快慢的。
     */
    // parse taget artist
    private final String TagAr = "[ar:";

    // perse taget tittle
    private final String TagTi = "[ti:";

    // perse target album
    private final String TagAl = "[al:";

    // perse target author of the lrc
    private final String TagBy = "[by:";

    // perse taget offset
    private final String TagOff = "[offset:";


    // get lrc artist
    public static final int ARTIST_ZONE = 0;

    // get lrc tittle
    public static final int TITTLE_ZONE = 1;

    // get lrc album
    public static final int ALBUM_ZONE = 2;

    // get lrc author
    public static final int AOTHOR_ZONE = 3;

    // get lrc offset
    public static final int OFFSET_ZONE = 4;

    // get lrc
    public static final int LRC_ZONE = 5;


    /**
     * constract
     */
    public static List<LrcData> lyricAnalyze(String lrc) {
        return lyricAnalyze.LrcAnalyzeStart(lrc);
    }


    private long LrcAnalyzeTimeStringToValue(String time) {
        // System.out.println(time.substring(0, time.lastIndexOf(":")));
        // System.out.println(time.substring(time.indexOf(":") + 1,
        // time.lastIndexOf(".")));
        // System.out.println(time.substring(time.indexOf(".") + 1));

        long minute = Integer
                .parseInt(time.substring(0, time.lastIndexOf(":")));

        long second = Integer.parseInt(time.substring(time.indexOf(":") + 1,
                time.lastIndexOf(".")));

        long millisecond = Integer
                .parseInt(time.substring(time.indexOf(".") + 1));

        return (long) (minute * 60 * 1000 + second * 1000 + millisecond);
    }

    private LrcData LrcAnalyzeLine(String ContentLine) {
        LrcData lrcdata = new LrcData();
        if (ContentLine.indexOf(TagAr) != -1) {// whether artist or not
            lrcdata.setType(ARTIST_ZONE);
            lrcdata.setLrcLine(ContentLine.substring(
                    ContentLine.indexOf(':') + 1, ContentLine.lastIndexOf(']')));
            // System.out.println(lrcline.LrcLine);

        } else if (ContentLine.indexOf(TagAl) != -1) {// whether album or not
            lrcdata.setType(ALBUM_ZONE);
            lrcdata.setLrcLine(ContentLine.substring(
                    ContentLine.indexOf(':') + 1, ContentLine.lastIndexOf(']')));
            // System.out.println(lrcline.LrcLine);
        } else if (ContentLine.indexOf(TagTi) != -1) {// whether tittle or not
            lrcdata.setType(TITTLE_ZONE);
            lrcdata.setLrcLine(ContentLine.substring(
                    ContentLine.indexOf(':') + 1, ContentLine.lastIndexOf(']')));
        } else if (ContentLine.indexOf(TagBy) != -1) {// whether author or not
            lrcdata.setType(AOTHOR_ZONE);
            lrcdata.setLrcLine(ContentLine.substring(
                    ContentLine.indexOf(':') + 1, ContentLine.lastIndexOf(']')));
        } else if (ContentLine.indexOf(TagOff) != -1) {// whether offset or not
            lrcdata.setType(OFFSET_ZONE);
            lrcdata.setLrcLine(ContentLine.substring(
                    ContentLine.indexOf(':') + 1, ContentLine.lastIndexOf(']')));
        } else {// lrc content
            String[] cut = ContentLine.split("]");
            if (cut.length >= 2) {
                lrcdata.setType(LRC_ZONE);
                lrcdata.setTime(cut[0]
                        .substring(ContentLine.indexOf('[') + 1));
                lrcdata.setTimeMs(LrcAnalyzeTimeStringToValue(lrcdata.getTime()));
                lrcdata.setLrcLine(cut[cut.length - 1]);


            }
        }
        return lrcdata;
    }

    private List<LrcData> LrcAnalyzeStart(String lrc) {
        List<LrcData> LrcList = new ArrayList<>();
        String[] lyrics = lrc.split("\n");
        if (ObjectUtils.allNotNull(lyrics) && lyrics.length > 0) {

            for (String lyric : lyrics) {
                LrcList.add(LrcAnalyzeLine(lyric));
            }
        }
        return LrcList;
    }

}
