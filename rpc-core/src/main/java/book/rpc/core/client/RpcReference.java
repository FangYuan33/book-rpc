package book.rpc.core.client;

import book.rpc.core.proxy.ProxyFactory;
import book.rpc.core.proxy.impl.JDKProxyFactory;

/**
 * RPC引用
 *
 * @author FangYuan
 * @since 2024-03-03 18:10:09
 */
public class RpcReference {

    private ProxyFactory proxyFactory;

    public RpcReference() {
        proxyFactory = new JDKProxyFactory();
    }

    /**
     * 根据接口类型获取代理对象
     */
    public <T> T get(Class<T> tClass) throws Throwable {
        return proxyFactory.getProxy(tClass);
    }
}
