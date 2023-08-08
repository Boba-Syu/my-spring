package cn.bobasyu.test;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.service.UserService2;
import org.junit.Test;

public class BeanFactoryBeanTest {
    @Test
    public void prototypeTest() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring3.xml");
        applicationContext.registerShutdownHook();

        UserService2 userService01 = applicationContext.getBean("userService2", UserService2.class);
        UserService2 userService02 = applicationContext.getBean("userService2", UserService2.class);
        System.out.println(userService01.queryUserInfo());
        System.out.println(userService02.queryUserInfo());

        System.out.println(userService01);
        System.out.println(userService02);
        System.out.println(userService01 == userService02);
    }
}
