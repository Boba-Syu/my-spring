package cn.bobasyu.test.aop;

import cn.bobasyu.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("前置拦截：" + method.getName());
    }
}
