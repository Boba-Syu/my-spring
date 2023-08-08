package cn.bobasyu.springframework.beans.factory;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * 感知BeanFactory属性的接口
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
