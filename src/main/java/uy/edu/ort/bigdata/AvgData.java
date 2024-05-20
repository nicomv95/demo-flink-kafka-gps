package uy.edu.ort.bigdata;

import java.io.Serializable;

public class AvgData implements Serializable {

    
    
    public AvgData(String id_vehiculo, Integer velocidad) {
        this.velocidad = velocidad;
        this.id_vehiculo = id_vehiculo;
    }

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

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }
    
    @Override
    public String toString() {
        return "AvgData [velocidad=" + velocidad + ", id_vehiculo=" + id_vehiculo + "]";
    }
}
