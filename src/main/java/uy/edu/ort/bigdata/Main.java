package uy.edu.ort.bigdata;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;

import java.time.Duration;

public class Main {
    public static void main(String[] args) throws Exception {

        String bootstrapServer = args[0];
        String inputTopic = args[1];
        String outputTopic = args[2];

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<GpsData> kafkaSource = KafkaSource.<GpsData>builder()
            .setBootstrapServers(bootstrapServer)
            .setTopics(inputTopic)
            .setGroupId("flink-group")
            .setStartingOffsets(OffsetsInitializer.latest())
            .setValueOnlyDeserializer(new GpsDataDeserializationSchema())
            .build();

        KafkaSink<AvgData> kafkaSink = KafkaSink.<AvgData>builder()
        .setBootstrapServers(bootstrapServer)
        .setRecordSerializer(KafkaRecordSerializationSchema.builder()
            .setTopic(outputTopic)
            .setValueSerializationSchema(new AverageSpeedSerializationSchema())
            .build()
        ).setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
        .build();
        
        
        DataStream<GpsData> stream = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");

        stream
                .map(data -> Tuple3.of(data.getId_vehiculo(), data.getVelocidad(), 1)).returns(Types.TUPLE(Types.STRING, Types.INT, Types.INT))
                .keyBy(data -> data.f0)
                .window(SlidingProcessingTimeWindows.of(Duration.ofSeconds(30), Duration.ofSeconds(5)))
                .reduce((data1, data2) -> Tuple3.of(data1.f0, data1.f1 + data2.f1, data1.f2 + data2.f2))
                .map(data -> new AvgData(data.f0, data.f1 / data.f2)).returns(AvgData.class)
                .sinkTo(kafkaSink);
        env.execute();
    }
}