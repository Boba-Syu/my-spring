package cn.bobasyu.test;

import cn.bobasyu.springframework.aop.AdvisedSupport;
import cn.bobasyu.springframework.aop.MethodMatcher;
import cn.bobasyu.springframework.aop.TargetSource;
import cn.bobasyu.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.bobasyu.springframework.aop.framework.Cglib2AopProxy;
import cn.bobasyu.springframework.aop.framework.JdkDynamicAopProxy;
import cn.bobasyu.springframework.aop.framework.ReflectiveMethodInvocation;
import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.context.ApplicationContext;
import cn.bobasyu.springframework.context.support.ClassPathXmlApplicationContext;
import cn.bobasyu.test.aop.UserServiceInterceptor;
import cn.bobasyu.test.service.IUserService;
import cn.bobasyu.test.service.UserService;
import cn.bobasyu.test.service.UserService3;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopTest {

    @Test
    public void test_proxy_method() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new UserService3();
        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* cn.bobasyu.test.service.IUserService.*(..))");
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            return invocation.proceed();
                        } finally {
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod().getName());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });
        String result = proxy.queryUserInfo();
        System.out.println("测试结果：" + result);
    }
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

    @Test
    public void aopBeforeTest() throws BeansException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring5.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        userService.queryUserInfo();
//        System.out.println(userService.queryUserInfo());
    }
}
