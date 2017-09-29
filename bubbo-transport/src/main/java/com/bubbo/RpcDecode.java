package com.bubbo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by DELL on 2017/9/27.
 */
public class RpcDecode extends ByteToMessageDecoder {

    private static final Integer HEAD_LENGTH = 4;

    private Class<?> clazz;

    public RpcDecode(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < HEAD_LENGTH) {
            return;
        }

        byteBuf.markReaderIndex();

        int dataLength = byteBuf.readInt();
        if (dataLength < 0) {
            channelHandlerContext.close(); //关闭连接
            throw new RuntimeException("消息格式有误，无法读取消息长度");
        }
        if (byteBuf.readableBytes() < dataLength) {

            // 为解决消息过大而产生的tcp的拆包问题
            byteBuf.resetReaderIndex();
            return;
            /*channelHandlerContext.close();
            throw new RuntimeException("消息格式有误，消息长度有误");*/
        }
        byte[] body = new byte[dataLength];
        byteBuf.readBytes(body);
        Object o = SerializerFactory.load().deserialize(body, clazz);
        list.add(o);
    }
}
