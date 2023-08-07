package cn.bobasyu.springframework.context.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bobasyu.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.bobasyu.springframework.beans.factory.config.BeanPostProcessor;
import cn.bobasyu.springframework.context.ConfigurableApplicationContext;
import cn.bobasyu.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 *
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        // 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 在Bean实例化之前，执行BeanFactoryPostProcessor方法
        invokeBeanFactoryPostProcessors(beanFactory);
        // 将BeanPostProcessor提前注册到容器中
        registerBeanPostProcessors(beanFactory);
        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 在其他Bean对象实例化之前优先对BeanPostProcessor进行注册操作
     *
     * @param beanFactory
     * @throws BeansException
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
     *
     * @param beanFactory
     * @throws BeansException
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessorBeanFactory(beanFactory);
        }
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 创建BeanFactory，并加载BeanDefinition
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
