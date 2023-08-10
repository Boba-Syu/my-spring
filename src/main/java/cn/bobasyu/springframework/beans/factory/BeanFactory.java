package cn.bobasyu.springframework.beans.factory;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * Bean对象的工厂接口，声明了从工厂获得Bean对象的方法
 */
public interface BeanFactory {
    /**
     * 有参方式获取Bean
     *
     * @param name Bean名称
     * @return Bean对象实例
     * @throws BeansException Bean创建时异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 有参方式获取Bean
     *
     * @param name Bean名称
     * @param args 参数
     * @return Bean对象实例
     * @throws BeansException Bean创建时异常
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 按类型获取Bean对象
     *
     * @param name         Bean对象名称
     * @param requiredType Bean对象类型
     * @param <T>          Bean对象类型
     * @return Bean对象实例
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
