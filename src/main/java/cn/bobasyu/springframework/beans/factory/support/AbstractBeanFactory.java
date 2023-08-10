package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.factory.FactoryBean;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.config.BeanPostProcessor;
import cn.bobasyu.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.bobasyu.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Bean工厂的抽象类，使用默认的单例实现获取Bean对象，申明了创建Bean对象和获取Bean类型的方法
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 字符解析器容器
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) throws BeansException {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 生成Bean的方法，如果该Bean是BeanFactory，则生产对应的Bean对象
     *
     * @param beanInstance
     * @param beanName
     * @return
     * @throws BeansException
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) throws BeansException {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    /**
     * 创建Bean对象，在获取Bean的方法getBean中被调用，当容器中没有相应的Bean时，使用该方法创建Bean实例对象
     *
     * @param name           Bean对象名称
     * @param beanDefinition Bean对象的相关信息
     * @return Bean对象的实例
     * @throws BeansException Bean对象创建失败，即Bean对象实例化时出现的异常
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    /**
     * 获取Bean相关信息，在创建Bean对象时调用，通过该方法获取Bean信息并根据该信息创建Bean对象
     *
     * @param name Bean对象名称
     * @return Bean相关信息
     * @throws BeansException 无法获取Bean对象的信息时抛出异常
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
