package book.rpc.core.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 拆包处理器
 * 协议内容：魔数（4字节）+ 数据长度（4字节）+ 数据
 *
 * @author FangYuan
 * @since 2024-02-29 21:00:10
 */
public class SplitHandler extends LengthFieldBasedFrameDecoder {

    /**
     * 在协议中表示数据长度的字段在字节流首尾中的偏移量
     */
    private static final Integer LENGTH_FIELD_OFFSET = 4;

    /**
     * 表示数据长度的字节长度
     */
    private static final Integer LENGTH_FIELD_LENGTH = 4;

    /**
     * 数据长度后边的头信息中的字节偏移量
     */
    private static final Integer LENGTH_ADJUSTMENT = 0;

    /**
     * 表示从第一个字节开始需要舍去的字节数，在我们的协议中，不需要进行舍去
     */
    private static final Integer INITIAL_BYTES_TO_STRIP = 0;

    public SplitHandler() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }
}
