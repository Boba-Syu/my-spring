package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.DisposableBean;
import cn.bobasyu.springframework.beans.factory.ObjectFactory;
import cn.bobasyu.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean单例对象获取的默认实现，使用Map存储和获取单例Bean对象
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected static final Object NULL_OBJECT = new Object();

    /**
     * Bean容器一级缓存，用于存储Bean的完整实例化对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();
    /**
     * Bean容器二级缓存，用于存储Bean的非完整实例化对象
     */
    private Map<String, Object> earlySingletonObjects = new HashMap<>();
    /**
     * Bean容器三级缓存，用于存储Bean的代理对象
     */
    private Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
    /**
     * 销毁方法容器，存储Bean销毁方法
     */
    private Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) throws BeansException {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 如果没有完整对象，则从二级缓存中获取未完整实例化的对象
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                // 如果二级缓存中也没有，则从代理对象处生成未完成的实例化对象，并删除代理对象
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void destroySingletons() throws BeansException {
        String[] disposableBeanNames = this.disposableBeanMap.keySet().toArray(new String[0]);
        for (int i = disposableBeanNames.length - 1; i >= 0; --i) {
            String beanName = disposableBeanNames[i];
            DisposableBean disposableBean = this.disposableBeanMap.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
        this.earlySingletonObjects.remove(beanName);
        this.singletonFactories.remove(beanName);
    }

    /**
     * 添加代理对象到三级缓存，同时删除二级对象中相同名字的bean，因为代理对象会重新生成该名字的bean
     * 如果一级缓存中存在完整对象，则不进行任何操作
     *
     * @param beanName
     * @param singletonFactory
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 注册Bean销毁方法到容器中
     * 该方法放在这儿而不是AbstractAutowireCapableBeanFactory中是因为放在这里被AbstractApplicationContext的close方法使用
     *
     * @param beanName
     * @param bean
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        this.disposableBeanMap.put(beanName, bean);
    }
}
