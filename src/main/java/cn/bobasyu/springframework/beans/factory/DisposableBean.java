package cn.bobasyu.springframework.beans.factory;

/**
 * Bean销毁方法接口，该接口的实现类对象的名称保存在BeanDefinition中，以便在使用时能够找到对应的方法
 * 销毁方法有AbstractApplicationContext在注册虚拟机钩子后，在虚拟机关闭前执行
 */
public interface DisposableBean {
    /**
     * Bean 销毁方法
     *
     * @throws Exception
     */
    void destroy() throws Exception;
}
