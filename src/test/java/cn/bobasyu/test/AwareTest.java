package cn.bobasyu.test;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.service.UserService;
import org.junit.Test;

public class AwareTest {
    @Test
    public void test_xml() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.query();
        System.out.println("result：" + result);
        System.out.println("ApplicationContextAware："+userService.getApplicationContext());
        System.out.println("BeanFactoryAware："+userService.getBeanFactory());
        System.out.println("BeanName："+userService.getBeanName());
        System.out.println("BeanClassloader："+userService.getClassLoader());
    }
}
