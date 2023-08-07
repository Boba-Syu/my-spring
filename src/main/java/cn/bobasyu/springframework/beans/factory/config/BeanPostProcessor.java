package cn.bobasyu.springframework.beans.factory.config;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * 对Bean对象初始化前后进行处理的接口，提供Bean初始化前后对其进行操作的方法
 */
public interface BeanPostProcessor {
    /**
     * 在Bean对象执行初始化前对Bean实例对象进行操作
     *
     * @param bean     被操作的Bean对象
     * @param beanName 被操作的Bean对象的名称
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在Bean对象执行初始化后对Bean实例对象进行操作
     *
     * @param bean     被操作的Bean对象
     * @param beanName 被操作的Bean对象的名称
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
