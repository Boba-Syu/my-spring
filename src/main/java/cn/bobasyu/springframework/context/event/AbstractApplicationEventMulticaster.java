package cn.bobasyu.springframework.context.event;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.BeanFactory;
import cn.bobasyu.springframework.beans.factory.BeanFactoryAware;
import cn.bobasyu.springframework.context.ApplicationEvent;
import cn.bobasyu.springframework.context.ApplicationListener;
import cn.bobasyu.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 对事件广播器公用方法的提取，实现事件广播器的基本功能，具体广播方法交给具体的实现类
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    /**
     * 用于存储事件监听器
     */
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();
    private BeanFactory beanFactory;

    @Override
    public void AddApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.remove((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        applicationListeners.add(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 得到所有对指定事件感兴趣的监听器
     *
     * @param event 指定事件
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) throws BeansException {
        LinkedList<ApplicationListener> listeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : this.applicationListeners) {
            if (supportsEvent(listener, event)) {
                listeners.add(listener);
            }
        }
        return listeners;
    }

    /**
     * 判断监听器是否对事件感兴趣，即该监听器是否会去关注该事件
     *
     * @param listener 指定监听器
     * @param event    指定事件
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) throws BeansException {
        Class<? extends ApplicationListener> listenerClass = listener.getClass();
        // 如果是代理类，需要拿到父类的Class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass.getClass();
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName = null;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());

    }
}
