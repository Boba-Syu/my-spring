package cn.bobasyu.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源加载器接口，用于处理资源加载流
 */
public interface Resource {
    /**
     * 获取加载资源得到的输入流对象，从该输入流中的到BeanDefinition信息
     *
     * @return 文件资源对应的输入流信息
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
