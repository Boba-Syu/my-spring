package cn.bobasyu.springframework.core.convert.support;

import cn.bobasyu.springframework.core.convert.converter.Converter;
import cn.bobasyu.springframework.core.convert.converter.ConverterFactory;
import cn.bobasyu.springframework.util.NumberUtils;
import org.jetbrains.annotations.Nullable;

/**
 * String转数字类型的Converter生成工厂，能够生产String转换成指定数字类型的Converter
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }


    private static final class StringToNumber<T extends Number> implements Converter<String, T> {

        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        @Nullable
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            return NumberUtils.parseNumber(source, this.targetType);
        }
    }
}
