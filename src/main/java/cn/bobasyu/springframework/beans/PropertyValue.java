package cn.bobasyu.springframework.beans;

/**
 * Bean对象中的属性值
 */
public class PropertyValue {
    /**
     * Bean对象属性的名称
     */
    private final String name;
    /**
     * Bean对象中属性的实例化对象
     */
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
