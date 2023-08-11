package cn.bobasyu.springframework.core.convert.converter;

/**
 * 类型转换接口，将S类型转换为T类型
 */
public interface Converter<S, T> {
    /**
     * 对指定变量进行类型转换
     *
     * @param source 被转换类型的参数
     * @return
     */
    T convert(S source);
}
