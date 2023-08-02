package cn.bobasyu.test;


import cn.bobasyu.springframework.BeansException;
import cn.bobasyu.springframework.factory.factory.BeanDefinition;
import cn.bobasyu.springframework.factory.support.DefaultListableBeanFactory;
import cn.bobasyu.test.service.UserService;
import org.junit.Assert;
import org.junit.Test;

public class BeanFactoryTest {
    @Test
    public void beanFactoryTest() throws BeansException {
        // 初始化beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.hello();
        // 第二次获取bean，测试单例
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        userService2.hello();
        Assert.assertEquals(userService, userService2);
    }
}
