package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.core.io.DefaultResourceLoader;
import cn.bobasyu.springframework.core.io.ResourceLoader;

/**
 * 实现了BeanDefinition的接口的抽象类，将这两个方法暴露给外部进行调用，而在具体实现类中不需要关注这两个方法的实现，
 * 具体实现类只需要关注怎么加载（从哪里加载，如xml等）BeanDefinition到容器中即可
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    /**
     * BeanDefinition注册器，通过该注册器将BeanDefinition注册到容器中
     */
    private final BeanDefinitionRegistry registry;
    /**
     * 资源加载器，通过路径读取文件资源，获取加载的BeanDefinition
     */
    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }
}
