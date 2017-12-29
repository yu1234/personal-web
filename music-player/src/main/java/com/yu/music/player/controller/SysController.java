package com.yu.music.player.controller;

import com.yu.music.player.bean.Response;
import com.yu.music.player.bean.User;
import com.yu.music.player.dao.repositories.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sys")
public class SysController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/public/api/login")
    public Response<User> login(@RequestBody User user) {
        Response<User> response = new Response<>();
        // 获得当前用户

        return response;
    }

    @PostMapping("/public/api/register")
    public Mono<Response<User>> register(@RequestBody final User user) {
        Response<User> response = new Response<>();
        //信息校验
        if (!ObjectUtils.allNotNull(user.getUsername())) {
            response.setSuccess(false);
            response.setMsg("用户名不能为空");
            response.setData(null);
            return Mono.just(response);
        }
        if (!ObjectUtils.allNotNull(user.getPassword())) {
            response.setSuccess(false);
            response.setMsg("密码不能为空");
            response.setData(null);

            return Mono.just(response);
        }
        if (!ObjectUtils.allNotNull(user.getNickname())) {
            response.setSuccess(false);
            response.setMsg("昵称不能为空");
            response.setData(null);
            return Mono.just(response);
        }
        UserDetails exist = this.userRepository.findByUsername(user.getUsername()).block();
        if (ObjectUtils.allNotNull(exist)) {
            response.setSuccess(false);
            response.setMsg("用户名已存在");
            response.setData(null);
            return Mono.just(response);
        }
        //密码加密
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user).flatMap(user1 -> {
            response.setSuccess(true);
            response.setMsg("注册成功");
            response.setData(user1);
            return Mono.just(response);
        }).switchIfEmpty(Mono.create(s->{
            response.setSuccess(false);
            response.setMsg("注册失败，清重新注册");
            response.setData(null);
            s.success(response);
        }));
    }
}
