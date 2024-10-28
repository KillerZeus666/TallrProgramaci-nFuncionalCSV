import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private String nombreDepartamento;
    private double superficie;
    private int poblacionUrbana;
    private int poblacionRural;

    private List<Municipio> municipios;

    public Departamento(String nombreDepartamento, double superficie, int poblacionUrbana, int poblacionRural) {
        this.nombreDepartamento = nombreDepartamento;
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

    public int getPoblacionTotal() {
        return poblacionUrbana + poblacionRural;
    }

    public void printNombre() {
        System.out.println("Nombre: " + nombreDepartamento);
    }

    public double getPorcentajeUrbana() {
        return ((double) getPoblacionUrbana() / getPoblacionTotal()) * 100;
    }

    public double getPorcentajeRural() {
        return ((double) getPoblacionRural() / getPoblacionTotal()) * 100;
    }
}
