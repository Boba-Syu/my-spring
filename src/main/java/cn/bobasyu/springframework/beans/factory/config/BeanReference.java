package cn.bobasyu.springframework.beans.factory.config;

/**
 * Bean属性填充使用的接口
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
