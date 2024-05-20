package uy.edu.ort.bigdata;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class GpsDataDeserializationSchema implements DeserializationSchema<GpsData> {

    private static final long serialVersionUID = 1L;

    private transient ObjectMapper objectMapper;
    
    @Override
    public void open(InitializationContext context) {
        objectMapper = JsonMapper.builder().build().registerModule(new JavaTimeModule());
    }

    @Override
    public GpsData deserialize(byte[] message) throws IOException {
        return objectMapper.readValue(message, GpsData.class);
    }

    @Override
    public boolean isEndOfStream(GpsData nextElement) {
        return false;
    }

    @Override
    public TypeInformation<GpsData> getProducedType() {
        return TypeInformation.of(GpsData.class);
    }
}