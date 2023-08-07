package cn.bobasyu.springframework.context;

import cn.bobasyu.springframework.beans.BeansException;

/**
 *
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;

    /**
     * 销毁方法注册，用于将销毁方法定义在虚拟机中，注册的方法再虚拟机关闭前自动执行
     */
    void registerShutdownHook();

    /**
     * 手动执行的关闭方法，该方法会被registerShutdownHook注册
     */
    void close() throws BeansException;
}
