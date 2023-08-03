package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean单例对象获取的默认实现，使用Map存储和获取单例Bean对象
 */
public class DefaultSingletonBeanRegistry  implements SingletonBeanRegistry {
    /**
     * Bean容器，用于存储Bean的实例化对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

}
