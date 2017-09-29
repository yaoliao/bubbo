package com.bubbo;

import com.bubbo.annotation.BubboService;
import com.bubbo.zookeeper.ZkSerRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/9/29.
 */
public class RpcServer implements ApplicationContextAware {

    private static final Logger LOGGER = Logger.getLogger(RpcServer.class);

    private Map<String, Object> handlerMap = new HashMap<>();
    private ZkSerRegistry registry;
    private String serverAddress;

    public RpcServer(ZkSerRegistry registry, String serverAddress) {
        this.registry = registry;
        this.serverAddress = serverAddress;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(BubboService.class);
        if (MapUtils.isNotEmpty(map)) {
            for (Object bean : map.values()) {
                String value = bean.getClass().getAnnotation(BubboService.class).value().getName();
                handlerMap.put(value, bean);
            }
        }
    }

    public void init() {
        registry.init();
        bootstrap();
    }

    private void bootstrap() {

        NioEventLoopGroup work = new NioEventLoopGroup();
        NioEventLoopGroup boss = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(work, boss).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new RpcEncode(RpcResponse.class));
                        channel.pipeline().addLast(new RpcDecode(RpcRequest.class));
                        channel.pipeline().addLast(new RpcServerHandler(handlerMap));
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        String[] token = serverAddress.split(":");
        String host = token[0];
        Integer port = Integer.valueOf(token[1]);
        try {
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(host, port)).sync();
            registry.register(host, port);
            LOGGER.debug("bubbo rpc server 启动成功 [ " + host + " : " + port + " ]");
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("RpcServer 连接失败");
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

}
