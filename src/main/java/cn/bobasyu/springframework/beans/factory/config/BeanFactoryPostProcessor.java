package cn.bobasyu.springframework.beans.factory.config;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * Bean实例化前对其进行预处理的接口，提供修改BeanDefinition的方法
 */
public interface BeanFactoryPostProcessor {
    /**
     * 所有的BeanDefinition加载完成后而Bean对象实例化之前调用，提供修改BeanDefinition的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessorBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
