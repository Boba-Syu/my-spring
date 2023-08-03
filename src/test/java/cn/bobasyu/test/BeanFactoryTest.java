package cn.bobasyu.test;


import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.PropertyValue;
import cn.bobasyu.springframework.beans.PropertyValues;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.bobasyu.springframework.beans.factory.config.BeanReference;
import cn.bobasyu.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bobasyu.springframework.core.io.DefaultResourceLoader;
import cn.bobasyu.springframework.core.io.Resource;
import cn.bobasyu.springframework.core.io.ResourceLoader;
import cn.bobasyu.test.bean.User;
import cn.bobasyu.test.bean.User2;
import cn.bobasyu.test.mapper.UserMapper;
import cn.bobasyu.test.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class BeanFactoryTest {
    @Test
    public void beanFactoryTest() throws BeansException {
        // 初始化beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(User.class);
        beanFactory.registerBeanDefinition("user", beanDefinition);
        // 获取bean
        User user = (User) beanFactory.getBean("user");
        user.hello();
        // 第二次获取bean，测试单例
        User user2 = (User) beanFactory.getBean("user");
        user2.hello();
        Assert.assertEquals(user, user2);
    }

    @Test
    public void beanFactoryTest2() throws BeansException {
        // 初始化beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(User2.class);
        beanFactory.registerBeanDefinition("user2", beanDefinition);
        // 获取bean
        User2 user = (User2) beanFactory.getBean("user2", "hello");
        user.hello();
    }

    @Test
    public void beanFactoryTest3() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("userMapper", new BeanDefinition(UserMapper.class));
        // 设置属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "1"));
        propertyValues.addPropertyValue(new PropertyValue("userMapper", new BeanReference("userMapper")));
        // 注入Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.query();
    }

}
