package book.rpc.core.handler;

import book.rpc.core.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

import static book.rpc.core.common.CommonCacheService.PROVIDER_CLASS_MAP;

/**
 * 服务处理节点
 *
 * @author FangYuan
 * @since 2024-03-03 17:17:52
 */
public class ServiceHandler extends SimpleChannelInboundHandler<Message> {

    /**
     * 当接收到消息时调用，处理消息并发送响应
     *
     * @param channelHandlerContext 通道处理上下文
     * @param message               接收到的消息
     * @throws Exception 可能抛出的异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        // 根据消息中的目标服务名获取对应的服务对象
        Object service = PROVIDER_CLASS_MAP.get(message.getTargetServiceName());
        // 获取该服务对象的所有声明方法
        Method[] methods = service.getClass().getDeclaredMethods();

        // 初始化结果对象
        Object result = null;
        // 遍历所有方法
        for (Method method : methods) {
            // 如果方法名与消息中的目标方法名相同
            if (method.getName().equals(message.getTargetMethod())) {
                // 如果方法返回类型为void，直接调用方法
                if (Void.TYPE.equals(method.getReturnType())) {
                    method.invoke(service, message.getArgs());
                } else {
                    // 否则调用方法并获取结果
                    result = method.invoke(service, message.getArgs());
                }
            }
        }

        // 将处理结果设置到消息中
        message.setResponse(result);
        // 将消息写入通道并刷新
        channelHandlerContext.writeAndFlush(message);
    }

}
