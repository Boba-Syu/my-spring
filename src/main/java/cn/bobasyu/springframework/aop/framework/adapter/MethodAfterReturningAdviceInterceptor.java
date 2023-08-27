package cn.bobasyu.springframework.aop.framework.adapter;

import cn.bobasyu.springframework.aop.MethodAfterReturningAdvice;
import cn.bobasyu.springframework.aop.MethodThrowingAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 后置方法拦截器具体实现类
 */
public class MethodAfterReturningAdviceInterceptor implements MethodInterceptor {
    private MethodThrowingAdvice advice;

    public MethodAfterReturningAdviceInterceptor() {
    }

    public MethodAfterReturningAdviceInterceptor(MethodThrowingAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object o = null;
        try {
            o = invocation.proceed();
        } catch (Exception e) {
            this.advice.afterThrowing(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        }
        return o;
    }

    public MethodThrowingAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodThrowingAdvice advice) {
        this.advice = advice;
    }
}
