package book.rpc.core.handler;

import book.rpc.core.common.Constant;
import book.rpc.core.protocol.Message;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
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
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        byte[] content = JSONObject.toJSONString(message).getBytes();
        byteBuf.writeInt(Constant.MAGIC_NUMBER);
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
        list.add(byteBuf);
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNumber = byteBuf.readInt();
        // 魔数不对，关闭连接
        if (Constant.MAGIC_NUMBER != magicNumber) {
            channelHandlerContext.close();
            return;
        }

        // 数据长度
        int contentLength = byteBuf.readInt();
        // 创建数据内容字节数组
        byte[] content = new byte[contentLength];
        byteBuf.readBytes(content);
        Message message = JSONObject.parseObject(new String(content), Message.class);
        list.add(message);
    }

}
