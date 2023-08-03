package cn.bobasyu.springframework.core.io;

/**
 * 资源获取接口, 通过该接口获取路径对应的资源对象
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 通过资源路径获取资源对象
     *
     * @param location 资源路径
     * @return 资源对象
     */
    Resource getResource(String location);
}
