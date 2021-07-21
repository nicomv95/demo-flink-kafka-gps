package uy.edu.ort.bigdata;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {

        String bootstrapServer = args[0];
        String topic = args[1];

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServer);
        props.put("client.id", "flink-client");

        FlinkKafkaConsumer<GpsData> kafkaConsumer = new FlinkKafkaConsumer<>(topic, new GpsDataDeserializationSchema(), props);
        kafkaConsumer.setStartFromLatest();

        DataStream<GpsData> stream = env.addSource(kafkaConsumer);

        stream
                .map(data -> Tuple3.of(data.getIdVehiculo(), data.getVelocidad(), 1)).returns(Types.TUPLE(Types.STRING, Types.INT, Types.INT))
                .keyBy(data -> data.f0)
                .window(SlidingProcessingTimeWindows.of(Time.seconds(30), Time.seconds(5)))
                .reduce((data1, data2) -> Tuple3.of(data1.f0, data1.f1 + data2.f1, data1.f2 + data2.f2))
                .map(data -> Tuple2.of(data.f0, data.f1 / data.f2)).returns(Types.TUPLE(Types.STRING, Types.INT))
                .print();

        env.execute();
    }
}