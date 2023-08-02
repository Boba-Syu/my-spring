package cn.bobasyu.springframework;

/**
 * Bean对象的工厂接口，声明了从工厂获得Bean对象的方法
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
}
