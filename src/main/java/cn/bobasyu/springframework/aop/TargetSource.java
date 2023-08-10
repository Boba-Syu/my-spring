package cn.bobasyu.springframework.aop;

import cn.bobasyu.springframework.util.ClassUtils;

/**
 * 代理所使用的目标对象信息类
 */
public class TargetSource {
    /**
     * 被代理的对象，可以是元对象，也可以是代理对象
     */
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
