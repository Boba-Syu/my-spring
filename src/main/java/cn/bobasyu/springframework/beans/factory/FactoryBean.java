package cn.bobasyu.springframework.beans.factory;

/**
 * Bean的获取方式接口
 */
public interface FactoryBean<T> {
    /**
     * 获取Bean对象
     *
     * @return Bean对象
     * @throws Exception
     */
    T getObject() throws Exception;

    /**
     * 获取Bean对象的类型
     *
     * @return
     */
    Class<?> getObjectType();

    /**
     * 该工厂生成Bean是否为单例模式
     *
     * @return
     */
    boolean isSingleton();
}
