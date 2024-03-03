package book.rpc.core.server;

import book.rpc.core.common.CommonCacheService;
import book.rpc.core.handler.RpcMessageCodecHandler;
import book.rpc.core.handler.ServiceHandler;
import book.rpc.core.handler.SplitHandler;
import book.rpc.core.service.DataServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 *
 * @author FangYuan33
 * @since 2024-02-28 21:12:51
 */
public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.registryService(new DataServiceImpl());
            server.startApplication();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startApplication() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                // 拆包、解析、处理业务
                                .addLast(new SplitHandler())
                                .addLast(new RpcMessageCodecHandler())
                                .addLast(new ServiceHandler());
                    }
                });
        bootstrap.bind(9000).sync();
    }

    public void registryService(Object serviceBean) {
        // 注册服务
        CommonCacheService.PROVIDER_CLASS_MAP.put(serviceBean.getClass().getInterfaces()[0].getName(), serviceBean);
    }
}
