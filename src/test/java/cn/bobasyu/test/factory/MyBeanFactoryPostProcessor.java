package cn.bobasyu.test.factory;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.PropertyValue;
import cn.bobasyu.springframework.beans.PropertyValues;
import cn.bobasyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.bobasyu.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessorBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "byte dance"));
    }
}
