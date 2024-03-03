package book.rpc.core.common;

import book.rpc.core.protocol.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 通用缓存服务
 *
 * @author FangYuan
 * @since 2024-03-03 17:20:06
 */
public class CommonCacheService {
    /**
     * 服务提供者类缓存
     * key: 服务名称 value: 服务提供者类
     */
    public static final Map<String, Object> PROVIDER_CLASS_MAP = new HashMap<>();

    /**
     * 收到结果后的缓存
     * key: uuid value: result
     */
    public static final Map<String, Object> RESP_MAP = new HashMap<>();

    /**
     * 发送队列
     */
    public static final BlockingQueue<Message> SEND_QUEUE = new ArrayBlockingQueue<>(100);
}
