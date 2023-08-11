package cn.bobasyu.springframework.beans.factory.config;

import cn.bobasyu.springframework.beans.factory.HierarchicalBeanFactory;
import cn.bobasyu.springframework.core.convert.ConversionService;
import cn.bobasyu.springframework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 * 可获取 BeanPostProcessor、BeanClassLoader等的配置化接口
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 在其他Bean对象实例化之前优先对BeanPostProcessor进行注册操作，以方便对Bean实例化前后进行相应操作
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 添加字符解析器到容器中
     *
     * @param valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 解析属性，使用字符解析器
     *
     * @param value
     * @return
     */
    String resolveEmbeddedValue(String value);

    void setConversionService(ConversionService conversionService);

    @Nullable
    ConversionService getConversionService();
}
