package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.PropertyValue;
import cn.bobasyu.springframework.beans.PropertyValues;
import cn.bobasyu.springframework.beans.factory.*;
import cn.bobasyu.springframework.beans.factory.config.*;
import cn.bobasyu.springframework.core.convert.ConversionService;
import cn.bobasyu.springframework.util.ClassUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 实现了创建Bean和自动配置的方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    /**
     * Bean对象的实例化策略，这里使用的是Cglib实例化的实现
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
//            // 判断是否返回代理 Bean 对象
//            bean = resolveBeforeInstantiation(name, beanDefinition);
//            if (bean != null) {
//                return bean;
//            }
            // 实例化Bean
            bean = createBeanInstance(beanDefinition, name, args);

            // 处理循环依赖，将实例化后的Bean对象提前放入缓存中暴露出来
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(name, () -> getEarlyBeanReference(name, beanDefinition, finalBean));
            }

            // 实例化后判断
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(name, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            // 在设置Bean属性之前，允许BeanPostProcessor修改属性
            applyBeanPostProcessorsBeforeApplyingPropertyValues(name, bean, beanDefinition);
            // 填充属性
            applyPropertyValues(name, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(name, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed");
        }
        // 注册销毁方法
        registerDisposableBeanIfNecessary(name, bean, beanDefinition);
        // 单例模式则将Bean对象加到内存中，原型模式则不进行该操作，每次都生成新对象
        Object exposedObject = bean;
        if (beanDefinition.isSingleton()) {
            // 获取代理对象
            exposedObject = getSingleton(name);
            registerSingleton(name, exposedObject);
        }
        return exposedObject;
    }

    /**
     * 处理循环依赖，
     *
     * @param beanName
     * @param beanDefinition
     * @param bean
     * @return
     * @throws BeansException
     */
    private Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) throws BeansException {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if (exposedObject == null) {
                    return exposedObject;
                }
            }
        }
        return exposedObject;
    }

    /**
     * Bean 实例化后对于返回 false 的对象，不在执行后续设置 Bean 对象属性的操作
     *
     * @param beanName
     * @param bean
     * @return
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) throws BeansException {
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    /**
     * 在设置Bean属性之前，允许BeanPostProcessor修改属性
     * 从BeanPostProcessor中筛选出InstantiationAwareBeanPostProcessor接口的实现类，并调用相应的postProcessPropertyValues和addPropertyValue循环设置属性值信息
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (pvs != null) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * Bean初始化前执行，如果需要生成代理对象，则返回代理对象，否则返回null
     *
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;

    }

    /**
     * 判断是否有InstantiationAwareBeanPostProcessor接口的Bean如果有，则执行代理对象生成操作并返回代理对象，否则返回null
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessBeforeInstantiation(beanClass, String.valueOf(beanName));
                if (result != null) return result;
            }
        }
        return null;
    }

    /**
     * 为Bean注册销毁方法，判断其是否实现了销毁接口DisposableBean，如果是则进行注册
     *
     * @param name           Bean名称
     * @param bean           Bean对象
     * @param beanDefinition Bean的相关配置信息，在该方法中主要用于寻找配置中的Bean销毁方法
     */
    private void registerDisposableBeanIfNecessary(String name, Object bean, BeanDefinition beanDefinition) {
        // 非单例模式类型的Bean不执行销毁方法
        if (!beanDefinition.isSingleton())
            return;
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(name, new DisposableBeanAdapter(bean, name, beanDefinition));
        }
    }


    /**
     * 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
     *
     * @param name
     * @param bean
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(String name, Object bean, BeanDefinition beanDefinition) throws BeansException {
        // 执行Aware感知相关操作，处理实现了Aware接口的类
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoaderAware(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
        // 执行前置操作
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, name);
        // 执行Bean初始化方法
        try {
            invokeInitMethods(name, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + name + "] failed", e);
        }
        // 执行后置操作
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, name);
        return wrappedBean;
    }

    private ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    private void invokeInitMethods(String name, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 实现接口
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + name + "'");
            }
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) return result;
            result = current;
        }
        return result;
    }

    /**
     * 创建Bean实例化
     *
     * @param beanDefinition 被实例化的Bean的信息
     * @param beanName       Bean实例的名称
     * @param args           实例化时用到的参数
     * @return Bean实例对象
     * @throws BeansException 实例化出错时报异常
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeansException {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            if (args != null && args.equals(Optional.empty()) && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * 用于给Bean对象填充属性
     *
     * @param beanName       Bean对象名称
     * @param bean           Bean实例对象
     * @param beanDefinition Bean对象的相关信息，存有Bean对象的属性信息
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String propertyName = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    // 获取属性的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                } else {
                    // 类型转换, 将其转换为接口的类型，匹配接口
                    Class<?> sourceType = value.getClass();
                    Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), beanName);
                    ConversionService conversionService = getConversionService();
                    if (conversionService != null) {
                        if (conversionService.canConvert(sourceType, targetType)) {
                            value = conversionService.convert(value, targetType);
                        }
                    }
                }
                // 填充属性
                BeanUtil.setFieldValue(bean, propertyName, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName, e);
        }
    }
}
