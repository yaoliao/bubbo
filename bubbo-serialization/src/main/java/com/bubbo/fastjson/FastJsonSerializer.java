package com.bubbo.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bubbo.Serializer;

/**
 * Created by 小新 on 2017/10/9.
 */
public class FastJsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = JSON.toJSONBytes(object, SerializerFeature.SortField);
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T object = JSON.parseObject(bytes, clazz, Feature.SortFeidFastMatch);
        return object;
    }
}
