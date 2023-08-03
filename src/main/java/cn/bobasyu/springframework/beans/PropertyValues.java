package cn.bobasyu.springframework.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bean对象中的属性值列表
 */
public class PropertyValues {
    /**
     * 存储Bean对象中的属性值
     */
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (Objects.equals(pv.getName(), propertyName)) {
                return pv;
            }
        }
        return null;
    }
}
