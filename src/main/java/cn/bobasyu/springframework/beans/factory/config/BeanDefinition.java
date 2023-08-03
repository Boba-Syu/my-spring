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
