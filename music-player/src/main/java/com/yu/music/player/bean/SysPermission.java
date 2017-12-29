package com.yu.music.player.bean;

import com.yu.spider.music.bean.BaseObject;
import lombok.Data;

import java.util.List;

@Data
public class SysPermission extends BaseObject {
    private String username;//帐号
    private String nickname;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;
    private String salt;//加密密码的盐
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Boolean available = Boolean.FALSE;
    private List<String> roleIds;
}
