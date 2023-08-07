package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.core.io.Resource;
import cn.bobasyu.springframework.core.io.ResourceLoader;

/**
 * BeanDefinition读取接口， 用该接口的方法加载BeanDefinition
 */
public interface BeanDefinitionReader {
    /**
     * @return 获取BeanDefinition注册器对象，在注册BeanDefinition时（如读取xml文件时）使用
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器对象，在loadBeanDefinitions方法中调用，通过该方法获取加载器生成对应的Resource对象
     *
     * @return 资源加载器对象，可以用它来得到文件资源，用于加载Bean信息
     */
    ResourceLoader getResourceLoader();

    /**
     * 通过资源加载对象来加载BeanDefinition，其通过resource获取inputStream对象进行加载
     *
     * @param resource 资源加载对象，通过其获得inputStream来读取文件内容
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 通过多个资源加载对象来加载BeanDefinition，其通过resource获取inputStream对象进行加载
     *
     * @param resources 资源加载对象，通过其获得inputStream来读取文件内容
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * 通过资源地址来进行加载BeanDefinition，其先通过地址从ResourceLoader得到Resource，再调用同名的重载方法得到
     *
     * @param location 地址信息，通过该地址得到对应的文件资源信息
     * @throws BeansException
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * 通过多个资源地址来进行加载BeanDefinition
     *
     * @param locations 地址信息，通过该地址得到对应的文件资源信息
     * @throws BeansException
     */
    void loadBeanDefinitions(String... locations) throws BeansException;

}
