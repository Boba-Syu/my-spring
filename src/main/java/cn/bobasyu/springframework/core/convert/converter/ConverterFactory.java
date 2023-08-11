package cn.bobasyu.springframework.core.convert.converter;

/**
 * 类型转换工厂接口，用于生成类型转换器
 */
public interface ConverterFactory<S, R> {
    /**
     * 类型转换器生成函数
     *
     * @param targetType
     * @param <T>
     * @return
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
