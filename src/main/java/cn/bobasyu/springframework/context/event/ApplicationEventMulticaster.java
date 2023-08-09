package cn.bobasyu.springframework.context.event;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.ApplicationEvent;
import cn.bobasyu.springframework.context.ApplicationListener;

/**
 * 事件广播器接口
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加监听事件
     *
     * @param listener
     */
    void AddApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除监听事件
     *
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     *
     * @param event
     */
    void multicastEvent(ApplicationEvent event) throws BeansException;

    /**
     * 添加监听器
     *
     * @param listener
     */
    void addApplicationListener(ApplicationListener listener);
}
