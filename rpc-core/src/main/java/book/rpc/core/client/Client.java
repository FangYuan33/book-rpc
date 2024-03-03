package book.rpc.core.client;

import book.rpc.core.handler.ClientHandler;
import book.rpc.core.handler.RpcMessageCodecHandler;
import book.rpc.core.handler.SplitHandler;
import book.rpc.core.protocol.Message;
import book.rpc.interfaces.DataService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import static book.rpc.core.common.CommonCacheService.SEND_QUEUE;

/**
 * 客户端
 *
 * @author FangYuan
 * @since 2024-03-03 17:46:35
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.startClientApplication();

            RpcReference rpcReference = new RpcReference();
            DataService dataService = rpcReference.get(DataService.class);
            String res = dataService.test("RPC!");

            log.info("响应结果为：{}", res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void startClientApplication() throws InterruptedException {
        // 创建非阻塞IO客户端
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup).channel(NioSocketChannel.class).handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new SplitHandler())
                                .addLast(new RpcMessageCodecHandler())
                                .addLast(new ClientHandler());
                    }
                });

        ChannelFuture channel = bootstrap.connect("127.0.0.1", 9000).sync();
        // 启动异步发送消息的线程
        new Thread(new AsyncSendJob(channel.channel())).start();
        log.info("============ 客户端启动 ============");
    }

    static class AsyncSendJob implements Runnable {

        private final Channel channel;

        public AsyncSendJob(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 异步发送消息
                    Message message = SEND_QUEUE.take();
                    channel.writeAndFlush(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
