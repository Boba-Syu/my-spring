package cn.bobasyu.test;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bobasyu.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.bobasyu.springframework.context.ApplicationContext;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.factory.MyBeanFactoryPostProcessor;
import cn.bobasyu.test.factory.MyBeanPostProcessor;
import cn.bobasyu.test.service.UserService;
import org.junit.Test;

public class BeanPostProcessorTest {
    @Test
    public void BeanFactoryPostProcessorTest() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring2.xml");

        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessorBeanFactory(beanFactory);

        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.query();
        System.out.println(result);
    }

    @Test
    public void xmlApplicationContextTest() throws BeansException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.query();
        System.out.println(result);
    }
}
