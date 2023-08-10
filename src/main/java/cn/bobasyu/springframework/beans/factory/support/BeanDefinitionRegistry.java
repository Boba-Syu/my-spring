package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册Bean的接口，申明了根据Bean名称和类型创建Bean对象的方法
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册Bean信息，将Bean信息注册到容器中，当创建Bean对象是再从容器中获取Bean信息，根据这些信息创建Bean实例对象
     *
     * @param beanName       Bean对象名称
     * @param beanDefinition Bean相关信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断是否包含指定名称的BeanDefinition
     *
     * @param beanName Bean对象名称
     * @return 若存在返回true否则为false
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 根据Bean名称查询BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 返回注册表中所有的Bean名称
     *
     * @return 册表中所有的Bean名称的列表
     */
    String[] getBeanDefinitionNames();
}
