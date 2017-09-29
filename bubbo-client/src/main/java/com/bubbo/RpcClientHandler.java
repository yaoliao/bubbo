package com.bubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

/**
 * Created by DELL on 2017/9/28.
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger LOGER = Logger.getLogger(RpcClientHandler.class);

    private RpcResponse response;

    public RpcClientHandler(RpcResponse response) {
        this.response = response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        LOGER.info("[RpcClientHandler - channelRead0 - response] " + rpcResponse);
        response.setRequestID(rpcResponse.getRequestID());
        response.setError(rpcResponse.getError());
        response.setResult(rpcResponse.getResult());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGER.error("[RpcClientHandler exceptionCaught] " + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
