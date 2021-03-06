package com.yu.music.player.bean;

import com.yu.spider.music.bean.BaseObject;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class User extends BaseObject implements UserDetails {
    private String username;//帐号
    private String nickname;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;
    @DBRef
    private List<Authority> authorities;// 一个用户具有多个角色

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
