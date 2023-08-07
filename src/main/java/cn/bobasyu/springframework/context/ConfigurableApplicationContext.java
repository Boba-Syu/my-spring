package cn.bobasyu.springframework.context;

import cn.bobasyu.springframework.beans.BeansException;

/**
 *
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
