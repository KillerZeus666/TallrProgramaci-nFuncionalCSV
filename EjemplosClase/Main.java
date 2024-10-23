package EjemplosClase;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            // Leer el archivo Divipola.csv
            List<String> deptos = Files.readAllLines(Paths.get("Divipola.csv"));

            // Contar y listar departamentos
            List<String> nomDeptos = deptos.stream()
                    .skip(1) // Saltar la cabecera
                    .map(l -> l.split(";")[3]) // Obtener el nombre del departamento
                    .distinct() // Obtener solo los departamentos únicos
                    .collect(Collectors.toList());

            System.out.println("Los departamentos de Colombia son: " + nomDeptos.size());
            nomDeptos.forEach(System.out::println);

            // Agrupar municipios por departamento
            Map<String, Set<String>> mapaDeptos = deptos.stream()
                    .skip(1) // Saltar la cabecera
                    .map(l -> l.split(";"))
                    .collect(Collectors.groupingBy(
                            columns -> columns[3], // Columna de departamento
                            Collectors.mapping(columns -> columns[4], Collectors.toSet()) // Columna de municipio
                    ));

            System.out.println("\nLos " + mapaDeptos.size() + " departamentos de Colombia ordenados por cantidad de municipios:");
            mapaDeptos.entrySet().stream()
                    .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()))
                    .forEach(entry -> System.out.println("Departamento: " + entry.getKey() + " - Municipios: " + entry.getValue().size()));
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
