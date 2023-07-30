package com.example.practica_4_aerolnea;

public class Vuelo {

    String origen;
    String destino;
    String fecha;
    String hora;
    String aerolinea;

    Vuelo(String origen, String destino, String fecha, String hora, String aerolinea) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.aerolinea = aerolinea;
    }

    @Override
    public String toString() {
        return "Origen: " + origen +
                ", Destino: " + destino +
                ", Fecha: " + fecha +
                ", Hora: " + hora +
                ", Aerolínea: " + aerolinea;
    }
}
