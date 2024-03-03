package book.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * PRC 消息
 * <p>
 * 消息格式：
 * +-----------------------+------------+----------+
 * | MAGIC_NUMBER(4 bytes) | contentLength(4 bytes) | content(n bytes) |
 * +-----------------------+------------+----------+
 * <p>
 * 通讯时会将 Message 对象转换成JSON串，其中 contentLength 表示JSON串字节数组长度
 *
 * @author FangYuan
 * @since 2024-02-29 21:11:46
 */
@Data
public class Message {

    /**
     * 要找的接口名称
     */
    private String targetServiceName;

    /**
     * 该接口中的方法
     */
    private String targetMethod;

    /**
     * 调用方法的参数
     */
    private Object[] args;

    /**
     * UUID 异步交互，用于对应请求和响应的结果
     */
    private String uuid;

    /**
     * 服务端的响应（如果有）
     */
    private Object response;

}
