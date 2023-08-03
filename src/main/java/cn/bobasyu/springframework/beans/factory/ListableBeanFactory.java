package cn.bobasyu.springframework.beans.factory;

import cn.bobasyu.springframework.beans.BeansException;

import java.util.Map;

/**
 * 对BeanFactory进行扩展
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 按照Bean的类型返回Bean实例对象
     *
     * @param type Bean的类型
     * @param <T>  Bean的类型
     * @return Bean实例对象
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 获取所有Bean的名称
     *
     * @return Bean名称数组
     */
    String[] getBeanDefinitionNames();
}
