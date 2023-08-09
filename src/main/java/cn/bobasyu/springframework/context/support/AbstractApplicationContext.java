package cn.bobasyu.springframework.context.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bobasyu.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.bobasyu.springframework.beans.factory.config.BeanPostProcessor;
import cn.bobasyu.springframework.context.ApplicationEvent;
import cn.bobasyu.springframework.context.ApplicationListener;
import cn.bobasyu.springframework.context.ConfigurableApplicationContext;
import cn.bobasyu.springframework.context.event.ApplicationEventMulticaster;
import cn.bobasyu.springframework.context.event.ContextClosedEvent;
import cn.bobasyu.springframework.context.event.ContextRefreshedEvent;
import cn.bobasyu.springframework.context.event.SimpleApplicationEventMulticaster;
import cn.bobasyu.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象的Spring上下文类，定义了Bean的生产和初始化等方法，以及事件监听等
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 添加ApplicationContextAwareProcessor对象处理ApplicationContextAware
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 在Bean实例化之前，执行BeanFactoryPostProcessor方法
        invokeBeanFactoryPostProcessors(beanFactory);
        // 将BeanPostProcessor提前注册到容器中
        registerBeanPostProcessors(beanFactory);
        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
        // 初始化事件发布者
        initApplicationEventMulticaster();
        // 注册事件监听器
        registerListeners();
        // 发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 发布容器刷新完成事件
     *
     * @throws BeansException
     */
    private void finishRefresh() throws BeansException {
        publishEvent(new ContextRefreshedEvent(this));
    }

    /**
     * 发布事件
     *
     * @param event 被发布的事件
     * @throws BeansException
     */
    public void publishEvent(ContextRefreshedEvent event) throws BeansException {
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 注册监听器
     *
     * @throws BeansException
     */
    private void registerListeners() throws BeansException {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster() throws BeansException {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                close();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void close() throws BeansException {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
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

    @Override
    public void publishEvent(ApplicationEvent event) throws BeansException {
        this.applicationEventMulticaster.multicastEvent(event);
    }
}
