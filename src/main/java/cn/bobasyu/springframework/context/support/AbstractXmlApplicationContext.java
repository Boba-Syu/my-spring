package cn.bobasyu.springframework.context.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.bobasyu.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 抽象上下文接口的Xml实现，但是留下了基于的配置文件地址获取接口给具体实现类
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 从入口上下文类拿到配置信息的地址描述
     *
     * @return
     */
    protected abstract String[] getConfigLocations();
}
