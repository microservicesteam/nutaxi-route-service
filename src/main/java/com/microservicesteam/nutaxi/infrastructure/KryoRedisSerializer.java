package com.microservicesteam.nutaxi.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoRedisSerializer implements RedisSerializer<Object> {

    private final Class<?> clazz;

    public KryoRedisSerializer(Class<?> clazz) {
        this.clazz = clazz;
    }

    private final Kryo kryo = new Kryo();

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Output output = new Output(stream);
        kryo.writeObject(output, obj);
        output.flush();

        return stream.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        Input input = new Input(stream);
        return kryo.readObject(input, clazz);
    }

}
