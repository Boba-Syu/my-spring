package cn.bobasyu.test;


import cn.bobasyu.springframework.BeansException;
import cn.bobasyu.springframework.factory.factory.BeanDefinition;
import cn.bobasyu.springframework.factory.support.DefaultListableBeanFactory;
import cn.bobasyu.test.bean.User;
import cn.bobasyu.test.bean.User2;
import org.junit.Assert;
import org.junit.Test;

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
}
