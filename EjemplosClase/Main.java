package EjemplosClase;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Collectors;
//Filtrar por un nombre de departamento
public class Main
{

    public static void main(String[] args) throws IOException
    {
        List<String> deptos =   Files.readAllLines(Paths.get("Divipola.csv"));


        Map<String, Set<String>> mapaDeptos = deptos.stream()
        .skip(1)
        .map(l -> l.split(";"))
        .collect(Collectors.groupingBy(
                        columns -> columns[0], // Agrupar por numero decolumna (cod_deptp)
                        Collectors.mapping(columns -> columns[3], Collectors.toSet()) // Obtener valores únicos)
                ));

         System.out.println("Los departamentos de Colombia son: " + mapaDeptos.size());

         mapaDeptos.entrySet().stream()
               .filter(entry -> entry.getKey().equals("QUINDÍO")) // Filtrar por clave
               .forEach(entry -> {
                    System.out.println("El Departamento de: " + entry.getKey() + " tiene " + entry.getValue().size() + " municipios:");
                    entry.getValue().forEach(element -> System.out.println("  " + element));
                });

    }
}