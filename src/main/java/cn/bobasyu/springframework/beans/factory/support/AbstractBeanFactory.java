package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.factory.BeanFactory;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.bobasyu.springframework.beans.BeansException;

import java.util.Optional;

/**
 * Bean工厂的抽象类，使用默认的单例实现获取Bean对象，申明了创建Bean对象和获取Bean类型的方法
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBean(name, Optional.empty());
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 创建Bean对象，在获取Bean的方法getBean中被调用，当容器中没有相应的Bean时，使用该方法创建Bean实例对象
     *
     * @param name           Bean对象名称
     * @param beanDefinition Bean对象的相关信息
     * @return Bean对象的实例
     * @throws BeansException Bean对象创建失败，即Bean对象实例化时出现的异常
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    /**
     * 获取Bean相关信息，在创建Bean对象时调用，通过该方法获取Bean信息并根据该信息创建Bean对象
     *
     * @param name Bean对象名称
     * @return Bean相关信息
     * @throws BeansException 无法获取Bean对象的信息时抛出异常
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;
}
