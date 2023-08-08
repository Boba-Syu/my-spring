package cn.bobasyu.springframework.beans.factory;

/**
 * 感知BeanName属性的接口
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String beanName);
}
