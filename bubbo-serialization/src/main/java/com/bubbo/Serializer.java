package com.bubbo;

/**
 * Created by DELL on 2017/9/27.
 */
public interface Serializer {

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
