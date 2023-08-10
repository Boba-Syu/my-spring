package cn.bobasyu.test;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.ApplicationContext;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.service.IUserService;
import org.junit.Test;

public class ComponentScanTest {
    @Test
    public void propertyTest() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
    }

    @Test
    public void scanTest() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void autowiredTest() throws BeansException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring6.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}
