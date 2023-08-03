package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化Bean对象的策略接口，申明了Bean对象使用何种方式实例化对象的方法
 */
public interface InstantiationStrategy {
    /**
     * 实例化Bean对象的方法
     *
     * @param beanDefinition Bean对象相关信息
     * @param beanName       Bean对象名称
     * @param ctor           Bean对象是使用的构造器，用于拿到和参数信息相对应的构造方法
     * @param args           Bean实例化时有参构造需要的参数
     * @return Bean的实例化对象
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
