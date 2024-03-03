package book.rpc.core.proxy;

import book.rpc.core.protocol.Message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static book.rpc.core.common.CommonCacheService.RESP_MAP;
import static book.rpc.core.common.CommonCacheService.SEND_QUEUE;

/**
 * JDK代理
 *
 * @author FangYuan
 * @since 2024-03-03 18:11:12
 */
public class JDKClientInvocationHandler implements InvocationHandler {

    // 默认返回值对象
    private final static Object OBJECT = new Object();

    private Class<?> clazz;

    public JDKClientInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 初始化消息对象
        Message message = new Message();
        message.setArgs(args);
        message.setTargetMethod(method.getName());
        message.setTargetServiceName(clazz.getName());
        message.setUuid(UUID.randomUUID().toString());

        // 放入发送队列准备发送消息
        SEND_QUEUE.add(message);
        // map 中保存UUID 映射返回结果
        RESP_MAP.put(message.getUuid(), OBJECT);
        // 3 秒内等待返回结果
        long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() - cur < 3 * 1000) {
            Object object = RESP_MAP.get(message.getUuid());
            if (object instanceof Message) {
                return ((Message) object).getResponse();
            }
        }

        throw new TimeoutException("client wait server's response timeout!");
    }
}
