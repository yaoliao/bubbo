package com.bubbo;

import com.bubbo.zookeeper.ZkSerDiscovery;
import com.bubbo.zookeeper.ZkSerRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Created by DELL on 2017/9/28.
 */
public class RpcClient {

    private static final Logger LOGGER = Logger.getLogger(RpcClient.class);

    private ZkSerDiscovery zkSerDiscovery;

    public RpcClient(ZkSerDiscovery zkSerDiscovery) {
        this.zkSerDiscovery = zkSerDiscovery;
    }

    public RpcResponse call(Class<?> clazz, Method method, Object[] parameters) {

        final RpcRequest rpcRequest = new RpcRequest(clazz.getName(), method.getName(), method.getParameterTypes(), parameters);
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        final RpcResponse rpcResponse = new RpcResponse();
        try {
            final CountDownLatch downLatch = new CountDownLatch(1);
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new RpcEncode(RpcRequest.class));
                    channel.pipeline().addLast(new RpcDecode(RpcResponse.class));
                    channel.pipeline().addLast(new RpcClientHandler(rpcResponse));
                }
            }).option(ChannelOption.SO_KEEPALIVE, true);

            String discover = zkSerDiscovery.discover();
            String[] token = discover.split(":");
            String host = token[0];
            Integer port = Integer.valueOf(token[1]);

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            future.channel().writeAndFlush(rpcRequest).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    downLatch.countDown();
                }
            });
            downLatch.await();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("RpcClient call 失败");
        } finally {
            group.shutdownGracefully();
        }
        return rpcResponse;
    }


}
