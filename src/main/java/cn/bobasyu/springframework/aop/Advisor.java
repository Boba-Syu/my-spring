package cn.bobasyu.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 整合切入点pointcut和advice，advice用于决定joinPoint执行什么操作
 */
public interface Advisor {

    Advice getAdvice();
}
