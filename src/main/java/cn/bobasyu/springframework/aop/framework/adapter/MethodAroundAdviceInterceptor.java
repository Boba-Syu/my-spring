package cn.bobasyu.springframework.aop.framework.adapter;

import cn.bobasyu.springframework.aop.MethodAroundAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 环绕通知方法拦截器具体实现类
 */
public class MethodAroundAdviceInterceptor implements MethodInterceptor {
    private MethodAroundAdvice advice;

    public MethodAroundAdviceInterceptor() {
    }

    public MethodAroundAdviceInterceptor(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        Object o = invocation.proceed();
        this.advice.after(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return o;
    }

    public MethodAroundAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAroundAdvice advice) {
        this.advice = advice;
    }
}
