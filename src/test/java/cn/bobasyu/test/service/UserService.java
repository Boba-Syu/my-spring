package cn.bobasyu.test.service;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.DisposableBean;
import cn.bobasyu.springframework.beans.factory.InitializingBean;
import cn.bobasyu.test.mapper.UserMapper;

public class UserService implements InitializingBean, DisposableBean {
    private String uId;
    private UserMapper userMapper;
    private String location;
    private String company;

    public String query() {
        return userMapper.queryUserName(uId);
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行:UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("执行:UserService.afterPropertiesSet");

    }
}
