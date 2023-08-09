package cn.bobasyu.test;

import cn.bobasyu.springframework.aop.AdvisedSupport;
import cn.bobasyu.springframework.aop.TargetSource;
import cn.bobasyu.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.bobasyu.springframework.aop.framework.Cglib2AopProxy;
import cn.bobasyu.springframework.aop.framework.JdkDynamicAopProxy;
import cn.bobasyu.test.aop.UserServiceInterceptor;
import cn.bobasyu.test.service.IUserService;
import cn.bobasyu.test.service.UserService;
import cn.bobasyu.test.service.UserService3;
import org.junit.Test;

import java.lang.reflect.Method;

public class AopTest {
    @Test
    public void aopTest() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* cn.bobasyu.test.service.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("query");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void proxyTest() {
        IUserService userService = new UserService3();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.bobasyu.test.service.IUserService.*(..))"));

        IUserService proxyByJdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println("jdk proxy: " + proxyByJdk.queryUserInfo());

        IUserService proxyByCglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println("cglib proxy: " + proxyByCglib.register("bobasyu"));
    }
}
