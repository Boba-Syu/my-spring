package cn.bobasyu.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean对象的工厂
 */
public class BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap;

    public BeanFactory() {
        this.beanDefinitionMap = new ConcurrentHashMap<>();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }
}
