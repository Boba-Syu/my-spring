package cn.bobasyu.springframework.beans.factory.config;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * Bean代理对象生成接口，继承了BeanPostProcessor接口，将在Bean初始化时创建对应的代理对象
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 生成代理对象，在Bean对象执行初始化时调用
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
