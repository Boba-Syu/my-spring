package cn.bobasyu.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceMethodInterceptor implements MethodInterceptor {
    private UserServiceAdvice advice;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        Object o = invocation.proceed();
        this.advice.after(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return o;
    }

    public UserServiceAdvice getUserServiceAdvice() {
        return advice;
    }

    public void setUserServiceAdvice(UserServiceAdvice advice) {
        this.advice = advice;
    }
}
