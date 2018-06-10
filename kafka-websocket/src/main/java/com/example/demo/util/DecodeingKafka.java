package com.example.demo.util;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

//反序列化
public class DecodeingKafka implements Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean isKey) {

    }

    @Override
    public Object deserialize(String s, byte[] data) {
        return BeanUtils.BytesToObject(data);
    }

    @Override
    public void close() {

    }
}
