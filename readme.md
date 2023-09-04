手写简单的spring

参考自
(https://github.com/fuzhengwei/small-spring)

## IoC部分
### Bean初始化和属性注入
Bean的信息封装在`BeanDefinition`中
```java
/**
 * 用于记录Bean的相关信息
 */
public class BeanDefinition {
    /**
     * Bean对象的类型
     */
    private Class beanClass;
    /**
     * Bean对象中的属性信息
     */
    private PropertyValues propertyValues;
    /**
     * 初始化方法的名称
     */
    private String initMethodName;
    /**
     * 销毁方法的名称
     */
    private String destroyMethodName;
    /**
     * 默认为单例模式
     */
    private String scope;
}
```
Bean注入过程中有一下几个主要接口：
- `BeanFactory`：Bean工厂接口，其中声明了获取Bean对象的方法
- `SingletonBeanRegistry`: Bean的单例模式创建接口，声明了单例对象的创建方法和销毁方法
- `InstantiationStrategy`：实例化Bean对象的策略接口，申明了Bean对象使用何种方式实例化对象的方法，在实现类中使用反射的方式获取构造方法来得到Bean对象，有JDK和Cglib两种实现
- `BeanDefinitionRegistry`：管理`BeanDefinition`的注册，其实现类中包含存有`BeanDefinition`的Map

其具体流程为：
从`BeanDefinitionRegistry`得到`BeanDefinition`信息，调用`BeanFactory`的`getBean()`方法，判断是否为单例模式，若为单例模式则调用`SingletonBeanRegistry`的`getSingleton()`方法，如果容器中存在Bean则直接返回，不存在则调用`InstantiationStrategy`的`instantiate()`方法，使用反射的方式生成Bean对象

在属性注入中，需要注入的属性信息封装在`BeanDefinition`的`PropertyValues`中，其本质为一个`PropertyValue`列表。

在创建Bean对象时，若存在无参构造方法，则使用无参构造方法，若没有，则从`PropertyValues`去除构造方法需要的属性。
```java
/**
 * Bean对象中的属性值
 */
public class PropertyValue {
    /**
     * Bean对象属性的名称
     */
    private String name;
    /**
     * Bean对象中属性的实例化对象
     */
    private Object value;
}
```

### 资源加载器
Spring需要解析配置文件，对此，定义了一下接口：
- `Resource`：资源信息接，用于处理资源加载流，其实现类根据配置文件地址得到资源信息，并向外声明有得到输入流的接口，其包含XML文件配置和URL文件配置等方式
- `ResourceLoader`：资源加载器接口，通过该接口获取路径对应的资源对象，用于获得资源对象
- `BeanDefinitionReader`：BeanDefinition读取接口，用于读取配置文件并调用`BeanDefinitionRegistry`加载`BeanDefinition`

### Bean生命周期
Bean的初始化操作，提供了Bean的初始化和销毁等方法接口，其包括：
- `InitializingBean`：提供了初始化方法的Bean，如果Bean实现了该接口，则会在创建Bean时调用初始化方法
- `DisposableBean`：提供了Bean销毁方法，若实现了该接口，则在容器销毁时调用其销毁方法

此外，在`BeanDefinition`中包含有`initMethodName`和`destroyMethodName`两个属性，用于指派Bean的初始化方法和销毁方法，可以在不实现上述接口的情况下使用反射的方式实现初始化和销毁方法，在XML中配置`init-method`和`destroy-method`两个属性即可

同时，定义有一下两个接口，来管理Bean的初始化操作
```java
/**
 * Bean实例化前对其进行预处理的接口，提供修改BeanDefinition的方法
 */
public interface BeanFactoryPostProcessor {
    /**
     * 所有的BeanDefinition加载完成后而Bean对象实例化之前调用，提供修改BeanDefinition的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessorBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
```
```java
/**
 * 对Bean对象初始化前后进行处理的接口，提供Bean初始化前后对其进行操作的方法
 */
public interface BeanPostProcessor {
    /**
     * 在Bean对象执行初始化前对Bean实例对象进行操作
     *
     * @param bean     被操作的Bean对象
     * @param beanName 被操作的Bean对象的名称
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在Bean对象执行初始化后对Bean实例对象进行操作
     *
     * @param bean     被操作的Bean对象
     * @param beanName 被操作的Bean对象的名称
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
```

### Bean工厂接口
对于复杂类型的Bean，其提供了`FactoryBean<T>`接口，对于实现了该接口的类，spring在创建Bean对象时，不是直接生成该对象，而是调用`getObject`方法来生成Bean对象
```java
/**
 * Bean对象的工厂接口，声明了从工厂获得Bean对象的方法
 */
public interface BeanFactory {
    /**
     * 有参方式获取Bean
     *
     * @param name Bean名称
     * @return Bean对象实例
     * @throws BeansException Bean创建时异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 有参方式获取Bean
     *
     * @param name Bean名称
     * @param args 参数
     * @return Bean对象实例
     * @throws BeansException Bean创建时异常
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 按类型获取Bean对象
     *
     * @param name         Bean对象名称
     * @param requiredType Bean对象类型
     * @param <T>          Bean对象类型
     * @return Bean对象实例
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
```

### Aware感知接口
对于实现了`Aware`接口的Bean，在spring创建实例时，会将对应的信息注入到Bean中，方便使用者对spring进行自定义扩展开发，其包括如下几类接口
- `BeanFactoryAware`
- `BeanClassLoaderAware`
- `BeanNameAware`
- `ApplicationContextAware`

### 事件监听机制
使用观察者模式，设置了事件监听机制
- `ApplicationEvent`：继承自`EventObject`接口，Spring Event事件抽象类，后续的所有事件类都继承自该类
- `ApplicationListener<E extends ApplicationEvent>`：继承自`EventListener`接口，其泛型类型为该监听器关注的事件类型
- `ApplicationEventMulticaster`：事件广播器接口，定义有添加和删除监听器以及广播监听事件的方法

在使用过程中，首先调用`ApplicationEventMulticaster`的`addApplicationListener`方法将自定义监听器加入到广播器中，当要发布事件时，调用`multicastEvent`方法，该方法会便利已注册的所有监听器，并向关注该事件的监听器发送通知，调用其`onApplicationEvent`方法

### 应用上下文
接口`ApplicationContext`整合了上述的各种方法，并提供了向外的操作接口。其中，`ApplicationContext`的实现类中定义了核心方法`refresh()`，其具体内容如下：
```java
    @Override
    public void refresh() throws BeansException {
        // 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 添加ApplicationContextAwareProcessor对象处理ApplicationContextAware
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 在Bean实例化之前，执行BeanFactoryPostProcessor方法
        invokeBeanFactoryPostProcessors(beanFactory);
        // 将BeanPostProcessor提前注册到容器中
        registerBeanPostProcessors(beanFactory);
        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
        // 初始化事件发布者
        initApplicationEventMulticaster();
        // 注册事件监听器
        registerListeners();
        // 发布容器刷新完成事件
        finishRefresh();
    }
```
### 三级缓存机制
为了解决循环依赖，spring使用了三级缓存，如下所示：
```java
    /**
     * Bean容器一级缓存，用于存储Bean的完整实例化对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();
    /**
     * Bean容器二级缓存，用于存储Bean的早期非完整实例化对象
     */
    private Map<String, Object> earlySingletonObjects = new HashMap<>();
    /**
     * Bean容器三级缓存，用于存储Bean的代理对象
     */
    private Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
    /**
```
其中，三级缓存存储的不是Bean对象，而是一个工厂对象，其目的是为了提早暴露Bean，等到后续的Bean依赖该Bean的时候，就会调用工厂的`getObject`方法获取到Bean对象。三级缓存的顺序如下：
```java
    @Override
    public Object getSingleton(String beanName) throws BeansException {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 如果没有完整对象，则从二级缓存中获取未完整实例化的对象
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                // 如果二级缓存中也没有，则从代理对象处生成未完成的实例化对象，并删除代理对象
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }
```

### 注解配置信息
这里给出了一下几个注解功能的实现：
- `@Component`：将类注入到容器中
- `@Autowired`：对Bean对象对应的该属性进行依赖注入
- `@Scope`：配置该类的作用域为单例还是原型
- `@Value`：注入配置文件中的信息

通过读取配置文件中`<context:component-scan />`指定的路径，对其下所有的类进行扫描，若标注有`@Component`注解，则生成对应的`BeanDefinition`，并读取其属性，生成对应的`PropertyValue`，若属性标注有`@Value`注解，表示属性值为配置文件中的对应数值，若标注有`@Autowired`注解，则表示属性值为Bean。生成好`BeanDefinition`后，就和原本流程一样了

## AOP
###  切点和匹配接口定义
AOP的核心是使用代理的方式生成代理对象，其核心接口包括：
- `ClassFilter`：定义类匹配类，用于切点找到给定的接口和目标类，提供了判断切入点是否应用在给定的接口或目标类中的方法
- `MethodMatcher`：方法匹配类，找到表达式范围内匹配下的目标类和方法，提供了判断给定的方法是否匹配表达式的方法
- `Pointcut`：切入点接口，定义用于获取`ClassFilter`、`MethodMatcher`的两个类
- `AopProxy`：代理接口，用于获取代理类

使用了JDK和AspectJ两种方式实现了上述接口，并通过`ProxyFactory`工厂类来对两种实现的选择进行了封装

### AOP融入Bean生命周期
将AOP融入Bean的声明周期，其利用了`BeanPostProcessor`接口，使用该接口的`postProcessAfterInitialization`对实例化后的原对象进行包装，返回其代理对象

其定义了`Advisor`访问者接口，整合切面pointcut和拦截器advice

对于反射要用的`advice`接口，定义了继承自该接口的`BeforeAdvcice`等接口，提供前置方法等AOP方法接口。同样地，对于拦截器`MethodInterceptor`，也封装了对应的`MethodBeforeAdviceInterceptor`等类，来实现各种AOP操作
