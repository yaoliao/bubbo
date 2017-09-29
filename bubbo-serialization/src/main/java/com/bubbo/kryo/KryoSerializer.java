package com.bubbo.kryo;

import com.bubbo.Serializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by DELL on 2017/9/27.
 */
public class KryoSerializer implements Serializer {

    public byte[] serialize(Object object) {
        Kryo kryo = new Kryo();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Output output = new Output(outputStream);
        kryo.writeObject(output, object);
        output.flush();
        output.close();
        byte[] bytes = outputStream.toByteArray();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(inputStream);
        T resule = kryo.readObject(input, clazz);
        input.close();
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resule;
    }
}
