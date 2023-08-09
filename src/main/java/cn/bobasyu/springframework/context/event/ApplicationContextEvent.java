package cn.bobasyu.springframework.context.event;

import cn.bobasyu.springframework.context.ApplicationContext;
import cn.bobasyu.springframework.context.ApplicationEvent;

/**
 * Spring中定义事件的基类，所有用户自定义事件都需要继承该类
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
