package cn.bobasyu.springframework.factory.support;

import cn.bobasyu.springframework.BeansException;
import cn.bobasyu.springframework.factory.factory.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 自动生成Bean的抽象类，实现了创建Bean方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            Constructor beanConstructor = beanDefinition.getBeanClass().getDeclaredConstructor();
            beanConstructor.setAccessible(true);
            bean = beanConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        addSingleton(name, bean);
        return bean;
    }
}
