package cn.bobasyu.springframework.beans.factory;

import cn.bobasyu.springframework.beans.BeansException;

/**
 * 对象生产接口，在addSingletonFactory使用，作用是依赖该接口生成从代理工厂生成Bean
 *
 * @param <T>
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
