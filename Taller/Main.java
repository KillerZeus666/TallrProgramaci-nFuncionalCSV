package Taller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Municipio> municipios = new ArrayList<>();
        String path = "DIVIPOLA.csv";

        // Cargar datos desde el archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Salta la primera línea (encabezados)
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                String codigoDaneMunicipio = fields[2];
                String nombreMunicipio = fields[3];
                double superficie = Double.parseDouble(fields[4]);
                int poblacionUrbana = Integer.parseInt(fields[5]);
                int poblacionRural = Integer.parseInt(fields[6]);
                
                municipios.add(new Municipio(codigoDaneMunicipio, nombreMunicipio, superficie, poblacionUrbana, poblacionRural));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Agrupar municipios por departamento (simulado)
        Map<String, List<Municipio>> departamentos = new HashMap<>();
        for (Municipio municipio : municipios) {
            String nombreDepartamento = obtenerNombreDepartamento(municipio.getCodigoDaneMunicipio()); // Método para obtener el departamento
            departamentos.computeIfAbsent(nombreDepartamento, k -> new ArrayList<>()).add(municipio);
        }

        // Calcular estadísticas para cada departamento
        for (Map.Entry<String, List<Municipio>> entry : departamentos.entrySet()) {
            String nombreDepartamento = entry.getKey();
            List<Municipio> municipiosDepto = entry.getValue();

            // Calcular estadísticas
            double totalSuperficie = municipiosDepto.stream().mapToDouble(Municipio::getSuperficie).sum();
            int totalPoblacionUrbana = municipiosDepto.stream().mapToInt(Municipio::getPoblacionUrbana).sum();
            int totalPoblacionRural = municipiosDepto.stream().mapToInt(Municipio::getPoblacionRural).sum();
            int totalPoblacion = totalPoblacionUrbana + totalPoblacionRural;

            // Calcular densidades
            double densidadTotal = totalPoblacion / totalSuperficie;
            double densidadUrbana = totalPoblacionUrbana / totalSuperficie;
            double densidadRural = totalPoblacionRural / totalSuperficie;

            // Calcular porcentajes
            double porcentajeUrbano = (double) totalPoblacionUrbana / totalPoblacion * 100;
            double porcentajeRural = (double) totalPoblacionRural / totalPoblacion * 100;

            // Calcular área promedio
            double areaPromedio = totalSuperficie / municipiosDepto.size();

            // Municipio más grande y más pequeño
            Municipio municipioMasGrande = municipiosDepto.stream().max(Comparator.comparingDouble(Municipio::getSuperficie)).orElse(null);
            Municipio municipioMasPequeno = municipiosDepto.stream().min(Comparator.comparingDouble(Municipio::getSuperficie)).orElse(null);

            // Imprimir estadísticas
            System.out.println("Departamento: " + nombreDepartamento);
            System.out.println("Densidad Total: " + densidadTotal);
            System.out.println("Densidad Urbana: " + densidadUrbana);
            System.out.println("Densidad Rural: " + densidadRural);
            System.out.println("Porcentaje Población Urbana: " + porcentajeUrbano);
            System.out.println("Porcentaje Población Rural: " + porcentajeRural);
            System.out.println("Área Promedio: " + areaPromedio);
            System.out.println("Municipio Más Grande: " + (municipioMasGrande != null ? municipioMasGrande.getNombreMunicipio() : "N/A"));
            System.out.println("Municipio Más Pequeño: " + (municipioMasPequeno != null ? municipioMasPequeno.getNombreMunicipio() : "N/A"));
            System.out.println();
        }
    }

    private static String obtenerNombreDepartamento(String codigoDane) {
//IMPLEMENTAR LOGICA
       return "Nombre del departamento"; // Reemplaza con la lógica adecuada.
    }
}
