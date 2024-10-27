public class Municipio {
    private String codigoDaneDepartamento;
    private String nombreDepartamento;
    private String codigoDaneMunicipio;
    private String nombreMunicipio;
    private double superficie;
    private int poblacionUrbana;
    private int poblacionRural;

    public Municipio(String codigoDaneDepartamento, String nombreDepartamento, String codigoDaneMunicipio,
                     String nombreMunicipio, double superficie, int poblacionUrbana, int poblacionRural) {
        this.codigoDaneDepartamento = codigoDaneDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.codigoDaneMunicipio = codigoDaneMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.superficie = superficie;
        this.poblacionUrbana = poblacionUrbana;
        this.poblacionRural = poblacionRural;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
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
