package Taller;

public class Municipio {
    private String codigoDaneMunicipio;
    private String nombreMunicipio;
    private double superficie;
    private int poblacionUrbana;
    private int poblacionRural;

    public Municipio(String codigoDaneMunicipio, String nombreMunicipio, double superficie, int poblacionUrbana, int poblacionRural) {
        this.codigoDaneMunicipio = codigoDaneMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.superficie = superficie;
        this.poblacionUrbana = poblacionUrbana;
        this.poblacionRural = poblacionRural;
    }

    public String getCodigoDaneMunicipio() {
        return codigoDaneMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public double getSuperficie() {
        return superficie;
    }

    public int getPoblacionUrbana() {
        return poblacionUrbana;
    }

    public int getPoblacionRural() {
        return poblacionRural;
    }
}
