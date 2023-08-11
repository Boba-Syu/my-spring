package cn.bobasyu.springframework.core.convert;

import org.jetbrains.annotations.Nullable;

/**
 * 类型转换抽象接口，提供类型转换检测和转换方法
 */
public interface ConversionService {
    /**
     * 判断是否能进行转换
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /**
     * 执行转换
     */
    <T> T convert(Object source, Class<T> targetType);

}
