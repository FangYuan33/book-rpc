package book.rpc.core.proxy.impl;

import book.rpc.core.proxy.JDKClientInvocationHandler;
import book.rpc.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * JDK代理工厂
 *
 * @author FangYuan
 * @since 2024-03-03 18:19:21
 */
public class JDKProxyFactory implements ProxyFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) throws Throwable {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new JDKClientInvocationHandler(clazz));
    }
}
