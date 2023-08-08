package cn.bobasyu.test.service;

import cn.bobasyu.test.mapper.IUserMapper;

public class UserService2 {
    private String uId;
    private String company;
    private String location;
    private IUserMapper userMapper;

    public String queryUserInfo() {
        return userMapper.queryUserName(uId) + "," + company + "," + location;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public IUserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(IUserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
