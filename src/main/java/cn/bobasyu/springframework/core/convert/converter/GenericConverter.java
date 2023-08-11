package cn.bobasyu.springframework.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

/**
 * 通用类型转换接口
 */
public interface GenericConverter {
    /**
     * 转换器的目标类型和原类型集合
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 将对象转换为指定类型
     *
     * @param source     指定对象
     * @param sourceType 对象原类型
     * @param targetType 转换的目标类型
     * @return
     */
    Object convert(Object source, Class sourceType, Class targetType);

    /**
     * 转换器类型结构，存有目标类型和原类型
     */
    final class ConvertiblePair {
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return this.sourceType;
        }

        public Class<?> getTargetType() {
            return this.targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != ConvertiblePair.class) {
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);

        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }

}
