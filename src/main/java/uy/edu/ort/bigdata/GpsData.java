package uy.edu.ort.bigdata;

import java.io.Serializable;

public class GpsData implements Serializable {

    private Integer velocidad;
    private String idVehiculo;


    public Integer getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Integer velocidad) {
        this.velocidad = velocidad;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public String toString() {
        return "GpsData{" +
                "velocidad=" + velocidad +
                ", id_vehiculo='" + idVehiculo + '\'' +
                '}';
    }
}
