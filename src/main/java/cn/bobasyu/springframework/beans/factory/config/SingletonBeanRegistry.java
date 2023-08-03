package cn.bobasyu.springframework.beans.factory.config;

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
}
