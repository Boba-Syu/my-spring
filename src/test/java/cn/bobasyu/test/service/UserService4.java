package cn.bobasyu.test.service;

import cn.bobasyu.springframework.beans.factory.annotation.Autowired;
import cn.bobasyu.springframework.beans.factory.annotation.Value;
import cn.bobasyu.springframework.stereotype.Component;
import cn.bobasyu.test.mapper.UserMapper;


@Component("userService")
public class UserService4 implements IUserService {
    @Value("${token}")
    private String token;
    @Autowired
    private UserMapper userMapper;

    @Override
    public String queryUserInfo() {
        return userMapper.queryUserName("1") + ", token=" + token;
    }

    @Override
    public String register(String userName) {
        return null;
    }
}