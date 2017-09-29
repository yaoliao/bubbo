package com.bubbo;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by DELL on 2017/9/28.
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = Logger.getLogger(RpcServerHandler.class);

    private Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        LOGGER.info("[RpcServerHandler rpcRequest] " + rpcRequest);
        RpcResponse response = new RpcResponse();
        response.setRequestID(rpcRequest.getRequestID());
        response.setError(null);
        Object result = handel(rpcRequest);
        response.setResult(result);
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handel(RpcRequest rpcRequest) {
        String className = rpcRequest.getClassName();
        Object bean = handlerMap.get(className);
        Object result = null;
        if (bean == null) {
            throw new IllegalArgumentException("[RpcServerHandler handel] 参数有误 该类名无对应类");
        }
        /*Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().equals(rpcRequest.getMethodName())) {
                try {
                    result = m.invoke(bean, rpcRequest.getParameters());
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    LOGGER.error("[RpcServerHandler handel] 反射获取结果失败");
                }
            }
        }*/

        try {
            Method method = bean.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            result = method.invoke(bean, rpcRequest.getParameters());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOGGER.error("[RpcServerHandler handel] 反射获取结果失败");
        }
        return result;
    }
}
