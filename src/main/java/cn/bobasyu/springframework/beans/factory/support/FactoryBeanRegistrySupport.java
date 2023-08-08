package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FactoryBean注册类，将FactoryBean注册到容器中，处理FactoryBean对象的注册操作
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /**
     * 存放来自FactoryBean的单例对象
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 从缓存中取出FactoryBean
     *
     * @param beanName FactoryBean对象名称
     * @return
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return object != NULL_OBJECT ? object : null;
    }

    /**
     * 从FactoryBean中生成指定的Bean对象实例
     *
     * @param factoryBean Bean对象生产工厂
     * @param beanName    被生产的Bean对象名称
     * @return 生产出来的Bean对象
     * @throws BeansException
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) throws BeansException {
        if (factoryBean.isSingleton()) {
            // 若为单例模式则先看缓存中是否以存在，若不存在则生产出Bean放入缓存中
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                object = object != NULL_OBJECT ? object : null;
                this.factoryBeanObjectCache.put(beanName, object);
            }
            return object;
        } else {
            // 原型模式直接生产
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factoryBean, String name) throws BeansException {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + name + "] creation", e);
        }
    }

}
