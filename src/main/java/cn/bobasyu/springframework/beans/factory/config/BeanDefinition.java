package cn.bobasyu.springframework.beans.factory.config;

import cn.bobasyu.springframework.beans.PropertyValues;

/**
 * 用于记录Bean的相关信息
 */
public class BeanDefinition {
    /**
     * Bean对象的类型
     */
    private Class beanClass;
    /**
     * Bean对象中的属性信息
     */
    private PropertyValues propertyValues;
    /**
     * 初始化方法的名称
     */
    private String initMethodName;
    /**
     * 销毁方法的名称
     */
    private String destroyMethodName;

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
}
