package uy.edu.ort.bigdata;

import java.io.Serializable;
import java.time.Instant;

public class GpsData implements Serializable {

    private Instant timestamp;
    private Double  latitud;
    private Double longitud;
    private Integer velocidad;
    private String id_vehiculo;

    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String idVehiculo) {
        this.id_vehiculo = idVehiculo;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "GpsData [timestamp=" + timestamp + ", latitud=" + latitud + ", longitud=" + longitud + ", velocidad="
                + velocidad + ", id_vehiculo=" + id_vehiculo + "]";
    }

}
