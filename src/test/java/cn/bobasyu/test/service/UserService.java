package cn.bobasyu.test.service;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.*;
import cn.bobasyu.springframework.context.ApplicationContext;
import cn.bobasyu.springframework.context.ApplicationContextAware;
import cn.bobasyu.test.mapper.UserMapper;

public class UserService implements InitializingBean, DisposableBean, BeanClassLoaderAware, BeanNameAware, BeanFactoryAware, ApplicationContextAware {
    private String uId;
    private UserMapper userMapper;
    private String location;
    private String company;
    private ClassLoader classLoader;
    private String beanName;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;


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

    @Override
    public void setBeanClassLoaderAware(ClassLoader beanClassLoaderAware) throws BeansException {
        this.classLoader = beanClassLoaderAware;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getBeanName() {
        return beanName;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
