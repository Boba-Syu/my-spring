package cn.bobasyu.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法前置接口
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 给定方法的前置回调
     *
     * @param method 被拦截的的方法
     * @param args   方法参数
     * @param target 方法执行的目标
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
