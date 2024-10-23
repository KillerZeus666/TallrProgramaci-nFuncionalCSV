package Taller;

import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Departamento {
    private String codigoDaneDepartamento;
    private Set<Municipio> municipios;

    public Departamento(String codigoDaneDepartamento) {
        this.codigoDaneDepartamento = codigoDaneDepartamento;
        this.municipios = new HashSet<>();
    }

    public String getCodigoDaneDepartamento() {
        return codigoDaneDepartamento;
    }

    public void agregarMunicipio(Municipio municipio) {
        this.municipios.add(municipio);
    }

    public void mostrarEstadisticas() {
        double totalSuperficie = municipios.stream().mapToDouble(Municipio::getSuperficie).sum();
        int totalPoblacionUrbana = municipios.stream().mapToInt(Municipio::getPoblacionUrbana).sum();
        int totalPoblacionRural = municipios.stream().mapToInt(Municipio::getPoblacionRural).sum();
        int totalPoblacion = totalPoblacionUrbana + totalPoblacionRural;

        if (totalPoblacion > 0) {
            double densidadTotal = totalPoblacion / totalSuperficie;
            double porcentajeUrbano = (double) totalPoblacionUrbana / totalPoblacion * 100;
            double porcentajeRural = (double) totalPoblacionRural / totalPoblacion * 100;

            System.out.println("Departamento: " + codigoDaneDepartamento);
            System.out.printf("Densidad Total: %.2f\n", densidadTotal);
            System.out.printf("Porcentaje Población Urbana: %.2f%%\n", porcentajeUrbano);
            System.out.printf("Porcentaje Población Rural: %.2f%%\n", porcentajeRural);
            System.out.println();
        }
    }
}
