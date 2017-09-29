package com.bubbo.proxy;

import com.bubbo.RpcClient;
import com.bubbo.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by DELL on 2017/9/28.
 */
public class RpcProxy implements InvocationHandler {

    private Class<?> clazz;
    private RpcClient rpcClient;

    public RpcProxy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcResponse rpcResponse = rpcClient.call(clazz, method, args);
        return rpcResponse.getResult();
    }

    public Object newProxy(Class<?> clazz) {
        this.clazz = clazz;
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, this);
    }
}
