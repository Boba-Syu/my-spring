package cn.bobasyu.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法环绕接口
 */
public interface MethodAroundAdvice extends MethodBeforeAdvice, MethodAfterAdvice {
}
