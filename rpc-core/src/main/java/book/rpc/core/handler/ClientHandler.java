package book.rpc.core.handler;

import book.rpc.core.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static book.rpc.core.common.CommonCacheService.RESP_MAP;

/**
 * 客户端处理器
 *
 * @author FangYuan
 * @since 2024-03-03 17:53:29
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    /**
     * 读取服务端返回的数据，并封装起来
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if (!RESP_MAP.containsKey(message.getUuid())) {
            throw new IllegalArgumentException("server response is error!");
        }
        RESP_MAP.put(message.getUuid(), message);
    }
}
