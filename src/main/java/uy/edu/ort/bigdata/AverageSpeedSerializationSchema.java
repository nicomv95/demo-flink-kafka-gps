package uy.edu.ort.bigdata;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

public class AverageSpeedSerializationSchema implements KafkaSerializationSchema<Tuple2<String, Integer>> {

    private String outputTopic;

    public AverageSpeedSerializationSchema(String outputTopic) {
        this.outputTopic = outputTopic;
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Tuple2<String, Integer> element, Long timestamp) {
        return new ProducerRecord<>(
                        outputTopic,
                        (Instant.now().toString() + "," + element.f0 + "," + element.f1).getBytes(StandardCharsets.UTF_8));
    }
    
}
