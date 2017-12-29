package com.yu.music.player.bean;

import lombok.Data;

@Data
public class Response<T> {
    private boolean success;//是否成功
    private String msg;//失败原因
    private T data;//响应结果


}
