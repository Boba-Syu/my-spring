package cn.bobasyu.springframework.core.convert.converter;

/**
 * 类型转换器注册接口
 */
public interface ConverterRegistry {
    /**
     * 添加类型转换器
     *
     * @param converter
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 添加通用类型转换器
     *
     * @param converter
     */
    void addConverter(GenericConverter converter);

    /**
     * 添加类型转换工厂
     *
     * @param converterFactory
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

}
