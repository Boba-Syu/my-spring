package cn.bobasyu.springframework.context;

import cn.bobasyu.springframework.beans.factory.Aware;

/**
 * 感知ApplicationContext属性的接口
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext);
}
