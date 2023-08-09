package cn.bobasyu.springframework.beans.factory.config;


import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.event.ApplicationEventMulticaster;

/**
 * Bean元素的单例注册接口，声明了获取单例Bean对象的方法
 */
public interface SingletonBeanRegistry {
    /**
     * 获取容器中单例Bean对象，在getBean方法中被调用
     *
     * @param beanName Bean对象的名称
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 单例对象销毁方法
     */
    void destroySingletons() throws BeansException;

    void registerSingleton(String beanName, Object singletonObject);
}
