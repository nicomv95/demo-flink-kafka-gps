package uy.edu.ort.bigdata;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.json.JsonMapper;

public class AverageSpeedSerializationSchema implements SerializationSchema<AvgData> {

    private transient ObjectMapper objectMapper;
    
    @Override
    public void open(InitializationContext context) throws Exception {
        objectMapper = JsonMapper.builder().build();
        SerializationSchema.super.open(context);
    }

    @Override
    public byte[] serialize(AvgData element) {
        try {
            return objectMapper.writeValueAsBytes(element);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}