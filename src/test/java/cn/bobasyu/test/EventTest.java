package cn.bobasyu.test;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.event.CustomEvent;
import org.junit.Test;

public class EventTest {
    @Test
    public void test_event() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring4.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }
}
