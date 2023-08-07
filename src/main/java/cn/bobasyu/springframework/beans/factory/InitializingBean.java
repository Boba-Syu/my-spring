package cn.bobasyu.springframework.beans.factory;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * Bean初始化方法接口，该接口的实现类对象的名称保存在BeanDefinition中，以便在使用时能够找到对应的方法
 */
public interface InitializingBean {
    /**
     * Bean初始化方法，在填充完Bean属性后调用
     *
     * @throws BeansException
     */
    void afterPropertiesSet() throws BeansException;
}
