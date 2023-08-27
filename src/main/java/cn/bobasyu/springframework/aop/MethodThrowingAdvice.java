package cn.bobasyu.springframework.aop;

import java.lang.reflect.Method;

/**
 * 异常后置接口
 */
public interface MethodThrowingAdvice {
    /**
     * 给定方法的异常回调
     *
     * @param method 被拦截的的方法
     * @param args   方法参数
     * @param target 方法执行的目标
     */
    void afterThrowing(Method method, Object[] args, Object target) throws Throwable;
}
