package com.yu.music.player.bean;

import com.yu.spider.music.bean.BaseObject;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class Authority extends BaseObject implements GrantedAuthority {
    private String authority; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    //角色 -- 权限关系：多对多关系;
    private List<Permission> permissions;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
