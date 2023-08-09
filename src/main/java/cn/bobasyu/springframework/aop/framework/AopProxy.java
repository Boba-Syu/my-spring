package cn.bobasyu.springframework.aop.framework;

/**
 * 代理接口，用于获取代理类
 */
public interface AopProxy {
    /**
     * 获取代理类对象
     */
    Object getProxy();
}
