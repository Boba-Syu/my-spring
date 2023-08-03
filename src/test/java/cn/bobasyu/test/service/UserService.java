package cn.bobasyu.test.service;

import cn.bobasyu.test.mapper.UserMapper;

public class UserService {
    private String uId;
    private UserMapper userMapper;

    public String query() {
        System.out.println("查询信息：" + userMapper.queryUserName(uId));
        return userMapper.queryUserName(uId);
    }
}
