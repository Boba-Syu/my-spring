package cn.bobasyu.springframework.aop;

/**
 * 切入点接口，用于获取ClassFilter和MethodMatcher
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
