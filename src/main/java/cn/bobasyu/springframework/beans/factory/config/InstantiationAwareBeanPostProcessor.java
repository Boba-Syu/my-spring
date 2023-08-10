package cn.bobasyu.springframework.beans.factory.config;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.PropertyValues;

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

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * Bean生成后的注解处理，处理Bean中标注了相关注解的属性和方法，将配置属性中的信息注入进去
     *
     * @param pvs      配置属性
     * @param bean     被处理的Bean
     * @param beanName Bean对象名称
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
