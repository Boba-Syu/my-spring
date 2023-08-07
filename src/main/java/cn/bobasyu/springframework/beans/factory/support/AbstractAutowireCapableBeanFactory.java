package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.PropertyValue;
import cn.bobasyu.springframework.beans.PropertyValues;
import cn.bobasyu.springframework.beans.factory.config.*;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Constructor;

/**
 * 自动生成Bean的抽象类，实现了创建Bean方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    /**
     * Bean对象的实例化策略，这里使用的是Cglib实例化的实现
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
            // 填充属性
            applyPropertyValues(name, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(name, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed");
        }
        addSingleton(name, bean);
        return bean;
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
        // 执行前置操作
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, name);
        // TODO
        invokeInitMethods(name, wrappedBean, beanDefinition);
        // 执行后置操作
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, name);
        return wrappedBean;
    }

    private void invokeInitMethods(String name, Object wrappedBean, BeanDefinition beanDefinition) {

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
            if (args != null && ctor.getParameterTypes().length == args.length) {
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
                }
                // 填充属性
                BeanUtil.setFieldValue(bean, propertyName, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName, e);
        }
    }
}
