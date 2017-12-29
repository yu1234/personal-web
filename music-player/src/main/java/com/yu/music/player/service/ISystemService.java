package com.yu.music.player.service;

public interface ISystemService {
    /**
     * 网易云手机登录
     * @param username
     * @param password
     * @throws Exception
     */
    void neteaseLoginPhone(String username,String password) throws Exception;
}
