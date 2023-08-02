package cn.bobasyu.test;

import cn.bobasyu.test.service.UserService;
import cn.bobasyu.springframework.BeanDefinition;
import cn.bobasyu.springframework.BeanFactory;

public class BeanFactoryTest {
    public static void main(String[] args) {
        // 注册bean
        BeanFactory beanFactory = new BeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.hello();
    }
}
