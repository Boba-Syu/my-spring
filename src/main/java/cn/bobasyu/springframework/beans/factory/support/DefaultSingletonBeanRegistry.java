package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.DisposableBean;
import cn.bobasyu.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean单例对象获取的默认实现，使用Map存储和获取单例Bean对象
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * Bean容器，用于存储Bean的实例化对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();
    /**
     * 销毁方法容器，存储Bean销毁方法
     */
    private Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
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

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 注册Bean销毁方法到容器中
     * 该方法放在这儿而不是AbstractAutowireCapableBeanFactory中是因为放在这里被AbstractApplicationContext的close方法使用
     *
     * @param beanName
     * @param disposableBeanAdapter
     */
    public void registerDisposableBean(String beanName, DisposableBeanAdapter disposableBeanAdapter) {
        this.disposableBeanMap.put(beanName, disposableBeanAdapter);
    }

}
