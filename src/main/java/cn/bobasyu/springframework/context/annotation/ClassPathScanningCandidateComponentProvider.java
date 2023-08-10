package cn.bobasyu.springframework.context.annotation;

import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.bobasyu.springframework.stereotype.Component;
import cn.hutool.core.util.ClassUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 对象扫描装配类
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 扫描包下所有带有@Component注解的类
     *
     * @param basePackage 被扫描的包
     * @return 指定包下带有该注解的类的集合
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }

        return candidates;
    }
}
