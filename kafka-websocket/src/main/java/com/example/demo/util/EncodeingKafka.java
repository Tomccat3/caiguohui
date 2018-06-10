package com.example.demo.util;


import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

//序列化
public class EncodeingKafka implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> map, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return BeanUtils.ObjectToBytes(data);
    }

    @Override
    public void close() {
    }
}
