package cn.bobasyu.springframework.aop;

/**
 * 用于找到给定的接口和目标类
 */
public interface ClassFilter {
    /**
     * 判断切入点是否应用在给定的接口或目标类中
     *
     * @param clazz 目标接口或目标类
     * @return
     */
    boolean matches(Class<?> clazz);
}
