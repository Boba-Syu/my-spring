package cn.bobasyu.springframework.context;

import cn.bobasyu.springframework.beans.factory.ListableBeanFactory;

/**
 * Spring上下文接口，从此接口开始扩展出一系列上下文的实现类
 */
public interface ApplicationContext extends ListableBeanFactory {
}
