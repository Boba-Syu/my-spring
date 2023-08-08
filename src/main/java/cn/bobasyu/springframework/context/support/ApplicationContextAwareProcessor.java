package cn.bobasyu.springframework.context.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.config.BeanPostProcessor;
import cn.bobasyu.springframework.context.ApplicationContext;
import cn.bobasyu.springframework.context.ApplicationContextAware;

/**
 * Aware包装处理类，在refresh操作时执行获得ApplicationContext对象，在Bean初始化前时对实现了ApplicationContextAware接口的Bean对象进行操作
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
