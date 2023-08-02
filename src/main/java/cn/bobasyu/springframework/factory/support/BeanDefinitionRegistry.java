package cn.bobasyu.springframework.factory.support;

import cn.bobasyu.springframework.factory.factory.BeanDefinition;

/**
 * 注册Bean的接口，申明了根据Bean名称和类型创建Bean对象的方法
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册Bean信息，将Bean信息注册到容器中，当创建Bean对象是再从容器中获取Bean信息，根据这些信息创建Bean实例对象
     * @param beanName Bean对象名称
     * @param beanDefinition Bean相关信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
