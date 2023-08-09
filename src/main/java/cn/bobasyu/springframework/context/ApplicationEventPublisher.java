package cn.bobasyu.springframework.context;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * 事件发布接口，定义了事件发布方法
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件方法
     *
     * @param event 被发布的事件
     */
    void publishEvent(ApplicationEvent event) throws BeansException;
}
