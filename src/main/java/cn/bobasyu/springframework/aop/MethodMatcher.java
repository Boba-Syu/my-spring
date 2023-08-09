package cn.bobasyu.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配接口，用于找到表达式范围内下的目标类和方法
 */
public interface MethodMatcher {
    /**
     * 判断给定的方法是否匹配表达式
     *
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
