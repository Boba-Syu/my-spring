package cn.bobasyu.springframework.context.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.bobasyu.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 主要实现了AbstractApplicationContext中的抽象方法，但是未实现具体的加载BeanDefinition方法，将其留给了具体的实现类
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 加载BeanDefinition的方法接口，集体实现类根据情况定义加载方式（注解，xml等）
     *
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }


}
