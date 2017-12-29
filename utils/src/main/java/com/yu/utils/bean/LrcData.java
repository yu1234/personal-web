package com.yu.utils.bean;

public class LrcData {
    private int type;
    private String time; // time of string format
    private long timeMs; // time of long format ms
    private String lrcLine; // one line lrc

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }

    public String getLrcLine() {
        return lrcLine;
    }

    public void setLrcLine(String lrcLine) {
        this.lrcLine = lrcLine;
    }
}
