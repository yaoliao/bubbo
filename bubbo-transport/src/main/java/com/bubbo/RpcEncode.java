package com.bubbo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by DELL on 2017/9/27.
 */
public class RpcEncode extends MessageToByteEncoder {

    private Class<?> clazz;

    public RpcEncode(Class<?> clazz) {
        this.clazz = clazz;
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (clazz.isInstance(o)) {
            byte[] bytes = SerializerFactory.load().serialize(o);
            int length = bytes.length;
            byteBuf.writeInt(length); //先写入消息长度
            byteBuf.writeBytes(bytes); //写入消息体
        }
    }
}
