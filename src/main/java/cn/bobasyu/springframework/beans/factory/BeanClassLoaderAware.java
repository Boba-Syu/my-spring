package cn.bobasyu.springframework.beans.factory;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * 感知BeanClassLoaderAware属性的接口
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoaderAware(ClassLoader beanClassLoaderAware) throws BeansException;
}
