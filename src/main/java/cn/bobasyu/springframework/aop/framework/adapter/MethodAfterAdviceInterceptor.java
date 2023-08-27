package cn.bobasyu.springframework.aop.framework.adapter;

import cn.bobasyu.springframework.aop.MethodAfterAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 后置方法拦截器具体实现类
 */
public class MethodAfterAdviceInterceptor implements MethodInterceptor {
    private MethodAfterAdvice advice;

    public MethodAfterAdviceInterceptor() {
    }

    public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 执行后置方法
        Object o = invocation.proceed();
        this.advice.after(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return o;
    }

    public MethodAfterAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodAfterAdvice advice) {
        this.advice = advice;
    }
}
