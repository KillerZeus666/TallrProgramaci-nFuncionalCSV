package EjemplosClase;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Collectors;

public class Municipio
{
    public static void main(String[] args) throws IOException
    {
        // Leer el archivo CSV
        List<String> deptos = Files.readAllLines(Paths.get("Divipola.csv"));

        // Agrupar los datos en un mapa usando el número de departamento (cod_depto)
        Map<String, Set<String>> mapaDeptos = deptos.stream()
            .skip(1) // Saltar la primera línea (encabezados)
            .map(l -> l.split(";")) // Separar por el delimitador ";"
            .collect(Collectors.groupingBy(
                columns -> columns[0], // Agrupar por el código de departamento
                Collectors.mapping(columns -> columns[4], Collectors.toSet()) // Obtener valores únicos (nombre de municipios)
            ));

        // Imprimir la cantidad de departamentos
        System.out.println("Los " + mapaDeptos.size() + " departamentos de Colombia ordenados por cantidad de municipios:");

        // Ordenar las claves por el tamaño de sus valores agrupados
        Map<String, Set<String>> datosAgrupados = mapaDeptos.entrySet().stream()
            .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size())) // Ordenar de mayor a menor
            .collect(Collectors.toMap(
                entry -> entry.getKey(), // Usar lambda para obtener la clave
                entry -> entry.getValue(), // Usar lambda para obtener el valor
                (oldValue, newValue) -> oldValue, // Mantener el valor existente en caso de colisión
                LinkedHashMap::new // Mantener el orden de inserción
            ));

        // Mostrar los resultados agrupados y ordenados
        datosAgrupados.forEach((key, value) -> {
            System.out.println("Departamento: " + key + " Municipios: " + value.size());
        });
    }
}
