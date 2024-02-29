package book.rpc.core.handler;

import book.rpc.core.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * RPC编解码处理器
 *
 * @author FangYuan
 * @since 2024-02-29 20:57:11
 */
@Slf4j
public class RpcMessageCodecHandler extends MessageToMessageCodec<ByteBuf, Message> {

    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {

    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNumber = byteBuf.readInt();
        // 魔数不对，关闭连接
        if (Message.MAGIC_NUMBER != magicNumber) {
            channelHandlerContext.close();
            return;
        }

        // 数据长度
        int contentLength = byteBuf.readInt();
        // 创建数据内容字节数组
        byte[] content = new byte[contentLength];
        byteBuf.readBytes(content);
        // 解析成消息
        Message message = new Message(content);
        list.add(message);
    }

    /**
     * 获取消息字节长度
     */
    private Integer getMessageBodyLength(ByteBuf byteBuf) {
        if (hasLength(byteBuf, Integer.BYTES)) {
            // 读取魔数后的消息长度
            return byteBuf.getInt(Integer.BYTES);
        }

        return null;
    }

    /**
     * 校验字节流是否包含字段信息
     */
    private boolean hasLength(ByteBuf byteBuf, int fieldLength) {
        return byteBuf.readableBytes() >= fieldLength;
    }
}
