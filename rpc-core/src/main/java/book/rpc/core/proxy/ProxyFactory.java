package book.rpc.core.proxy;

/**
 * 代理工厂
 *
 * @author FangYuan
 * @since 2024-03-03 18:17:30
 */
public interface ProxyFactory {

    /**
     * 获取代理对象
     */
    <T> T getProxy(final Class<T> clazz) throws Throwable;
}
