package cn.bobasyu.springframework.context;

import cn.bobasyu.springframework.beans.factory.HierarchicalBeanFactory;
import cn.bobasyu.springframework.beans.factory.ListableBeanFactory;
import cn.bobasyu.springframework.core.io.ResourceLoader;

/**
 * Spring上下文接口，从此接口开始扩展出一系列上下文的实现类
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
