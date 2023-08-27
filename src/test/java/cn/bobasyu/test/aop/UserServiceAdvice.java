package cn.bobasyu.test.aop;

import cn.bobasyu.springframework.aop.MethodAfterAdvice;
import cn.bobasyu.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class UserServiceAdvice implements MethodAfterAdvice, MethodBeforeAdvice {
    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("后置拦截：" + method.getName());
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("前置拦截：" + method.getName());
    }
}
