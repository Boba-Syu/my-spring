package cn.bobasyu.test.factory;

import cn.bobasyu.springframework.beans.factory.FactoryBean;
import cn.bobasyu.test.mapper.IUserMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory implements FactoryBean<IUserMapper> {
    @Override
    public IUserMapper getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("1", "AAA");
            hashMap.put("2", "BBB");
            hashMap.put("3", "CCC");
            return "ProxyBeanFactory:" + method.getName() + hashMap.get(args[0].toString());
        };
        return (IUserMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserMapper.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
