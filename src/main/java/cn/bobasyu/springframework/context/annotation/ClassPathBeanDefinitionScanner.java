package cn.bobasyu.springframework.context.annotation;

import cn.bobasyu.springframework.beans.factory.config.BeanDefinition;
import cn.bobasyu.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.bobasyu.springframework.stereotype.Component;
import cn.hutool.core.util.StrUtil;

import java.util.Set;

/**
 * 注解扫描类，继承自ClassPathScanningCandidateComponentProvider,在此基础上扩展了解析作用域和Bean名称等功能
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 扫描包下的所有带有@component注解的类，并根据@Scope解析器作用域，以及分析出Bean的名称
     *
     * @param basePackages 被扫描的包的路径，可以有多个
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            // 解析Bean名称
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析作用域
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    /**
     * 解析Bean名称，由@Component中的值决定，若没有值，则为类名的小驼峰形式
     *
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

    /**
     * 解析作用域，由@Scope的值指定，若没有则返回空，即默认为单例模式
     *
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) return scope.value();
        return StrUtil.EMPTY;
    }
}
