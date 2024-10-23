package Taller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DensidadPoblacion {

    public static void main(String[] args) {
        String filePath = "datosDivipola.csv";  

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            // Omitir la cabecera
            List<String[]> data = lines.stream()
                .skip(1)  // Saltar la primera línea (cabecera)
                .map(line -> line.split(";"))
                .collect(Collectors.toList());

            Map<String, Departamento> departamentos = data.stream()
                .map(arr -> new Municipio(arr[0], arr[1].trim(), arr[2].trim(), arr[3].trim(), 
                                          Double.parseDouble(arr[4]), 
                                          Integer.parseInt(arr[5]), 
                                          Integer.parseInt(arr[6])))
                .collect(Collectors.groupingBy(Municipio::getNombreDepartamento, 
                        Collectors.reducing(new Departamento("", 0, 0, 0), 
                            municipio -> new Departamento(municipio.getNombreDepartamento(), 
                                                           municipio.getSuperficie(), 
                                                           municipio.getPoblacionUrbana(), 
                                                           municipio.getPoblacionRural()),
                            (dep1, dep2) -> new Departamento(dep1.getNombreDepartamento(), 
                                                              dep1.getSuperficie() + dep2.getSuperficie(), 
                                                              dep1.getPoblacionUrbana() + dep2.getPoblacionUrbana(), 
                                                              dep1.getPoblacionRural() + dep2.getPoblacionRural()))));

            // Mostrar resultados
            departamentos.values().forEach(dep -> {
                double densidadTotal = dep.getPoblacionTotal() / dep.getSuperficie();
                double densidadUrbana = (double) dep.getPoblacionUrbana() / dep.getSuperficie();
                double densidadRural = (double) dep.getPoblacionRural() / dep.getSuperficie();

                System.out.printf("Departamento: %s\n", dep.getNombreDepartamento());
                System.out.printf("Densidad total: %.2f hab/km²\n", densidadTotal);
                System.out.printf("Densidad urbana: %.2f hab/km²\n", densidadUrbana);
                System.out.printf("Densidad rural: %.2f hab/km²\n\n", densidadRural);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Clase para representar un Municipio
class Municipio {
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

// Clase para representar un Departamento
class Departamento {
    private String nombreDepartamento;
    private double superficie;
    private int poblacionUrbana;
    private int poblacionRural;

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
}
