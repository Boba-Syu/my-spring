package cn.bobasyu.test;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.service.UserService;
import org.junit.Test;

public class InitAndDestroyTest {
    @Test
    public void test() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring2.xml");
        applicationContext.registerShutdownHook();

        UserService userService =  applicationContext.getBean("userService", UserService.class);
        String res = userService.query();
        System.out.println(res);
    }
}
