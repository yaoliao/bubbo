package com.bubbo;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by DELL on 2017/9/27.
 */
public class SerializerFactory {

    /**
     * SPI
     *
     * @return
     */
    public static Serializer load() {

        Iterator iterator = ServiceLoader.load(Serializer.class).iterator();
        Serializer serializer = null;
        while (iterator.hasNext()) {
            serializer = (Serializer) iterator.next();
        }
        return serializer;
    }

}
