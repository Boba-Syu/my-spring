package cn.bobasyu.springframework.beans.factory.support;

import cn.bobasyu.springframework.beans.BeansException;
import cn.bobasyu.springframework.beans.factory.DisposableBean;
import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;

/**
 * 销毁方法适配器，用于统一处理Bean销毁方法，在虚拟机关闭前执行
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, String destroyMethodName) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = destroyMethodName;
    }

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this(bean, beanName, beanDefinition.getDestroyMethodName());
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean) && "destroy".equals(destroyMethodName)) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (destroyMethod == null) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
