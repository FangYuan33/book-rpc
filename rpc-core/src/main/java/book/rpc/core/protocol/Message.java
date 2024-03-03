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

    private String targetMethod;

    private String targetServiceName;

    private Object[] args;

    private String uuid;

    private Object response;

}
