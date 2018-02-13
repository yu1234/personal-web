package com.yu.music.player.bean;

import com.yu.spider.music.bean.BaseObject;
import lombok.Data;

import java.util.List;

@Data
public class Permission extends BaseObject {
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
}
