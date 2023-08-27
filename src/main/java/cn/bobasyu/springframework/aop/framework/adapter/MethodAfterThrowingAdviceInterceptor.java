package cn.bobasyu.springframework.aop.framework.adapter;

import cn.bobasyu.springframework.aop.MethodAfterReturningAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 异常方法拦截器具体实现类
 */
public class MethodAfterThrowingAdviceInterceptor implements MethodInterceptor {
    private MethodAfterReturningAdvice advice;

    public MethodAfterThrowingAdviceInterceptor() {
    }

    public MethodAfterThrowingAdviceInterceptor(MethodAfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object o = null;
        try {
            // 执行后置方法
            o = invocation.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.advice.after(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return o;
    }

    public MethodAfterReturningAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAfterReturningAdvice advice) {
        this.advice = advice;
    }
}
