import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/datosDivipola.csv";

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
                    .collect(Collectors.groupingBy(
                            Municipio::getNombreDepartamento,
                            Collectors.collectingAndThen(
                                    Collectors.reducing(
                                            new Departamento("", 0, 0, 0),
                                            municipio -> new Departamento(
                                                    municipio.getNombreDepartamento(),
                                                    municipio.getSuperficie(),
                                                    municipio.getPoblacionUrbana(),
                                                    municipio.getPoblacionRural()
                                            ),
                                            (dep1, dep2) -> new Departamento(
                                                    dep1.getNombreDepartamento().isEmpty() ? dep2.getNombreDepartamento() : dep1.getNombreDepartamento(),
                                                    dep1.getSuperficie() + dep2.getSuperficie(),
                                                    dep1.getPoblacionUrbana() + dep2.getPoblacionUrbana(),
                                                    dep1.getPoblacionRural() + dep2.getPoblacionRural()
                                            )
                                    ),
                                    departamento -> departamento
                            )
                    ));

            // Mostrar resultados
            departamentos.values().forEach(dep -> {
                double densidadTotal = dep.getPoblacionTotal() / dep.getSuperficie();
                double densidadUrbana = (double) dep.getPoblacionUrbana() / dep.getSuperficie();
                double densidadRural = (double) dep.getPoblacionRural() / dep.getSuperficie();
                double porcentajeUrbana = dep.getPorcentajeUrbana();
                double porcentajeRural = dep.getPorcentajeRural();

                dep.printNombre();
                System.out.printf("Densidad total: %.2f hab/km²\n", densidadTotal);
                System.out.printf("Densidad urbana: %.2f hab/km²\n", densidadUrbana);
                System.out.printf("Densidad rural: %.2f hab/km²\n", densidadRural);
                System.out.println("Porcentaje de poblacion urbana: " + porcentajeUrbana);
                System.out.println("Porcentaje de poblacion rural: " + porcentajeRural);
                System.out.println();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}