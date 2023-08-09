package cn.bobasyu.springframework.context;

import java.util.EventObject;

/**
 * Spring Event事件抽象类，后续的所有事件类都继承自该类
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
