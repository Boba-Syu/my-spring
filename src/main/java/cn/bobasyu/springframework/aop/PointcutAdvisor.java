package cn.bobasyu.springframework.aop;

/**
 * 切面拦截器，pointcut用于获取连接点joinPoint
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
