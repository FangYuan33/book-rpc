package book.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * PRC 消息
 *
 * @author FangYuan
 * @since 2024-02-29 21:11:46
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 5359096060555795690L;

    public static final Integer MAGIC_NUMBER = 20240229;

    private int contentLength;

    private byte[] content;

    public Message(byte[] content) {
        this.content = content;
        this.contentLength = content.length;
    }

}
