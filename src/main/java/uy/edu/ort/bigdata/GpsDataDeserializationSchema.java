package uy.edu.ort.bigdata;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class GpsDataDeserializationSchema implements DeserializationSchema<GpsData> {

    private static final long serialVersionUID = 1L;


    @Override
    public GpsData deserialize(byte[] message) throws IOException {
        GpsData data = new GpsData();
        String csvData = new String(message);
        String[] dataArray = csvData.split(",");
        data.setVelocidad(Integer.parseInt(dataArray[3]));
        data.setIdVehiculo(dataArray[4]);

        return data;
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