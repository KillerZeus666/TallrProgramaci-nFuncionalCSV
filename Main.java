import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/datosDivipola.csv";

        try {
            // 1. Cargar datos desde el archivo CSV
            List<String[]> data = cargarDatos(filePath);

            // 2. Procesar datos y agrupar por departamento
            Map<String, Departamento> departamentos = procesarDatos(data);

            // 3. Mostrar resultados de densidad y porcentajes de población
            //mostrarResultados(departamentos);

            // 4. Agrupar por departamento y calcular el área promedio de los municipios
            //Map<String, Double> areaPorDepartamento = areaPromedioPorDepartamento(data);

            //5. Mostrar resultados del promedio de los municipios por departamento
            //mostrarResultados2(areaPorDepartamento);


            // Agrupar por departamento y encontrar el municipio más grande por superficie
            Map<String, Optional<String[]>> masGrandePorDepartamento = municipioMasGrandePorDepartamento(data);

            // Agrupar por departamento y encontrar el municipio más pequenio por superficie
            Map<String, Optional<String[]>> maspequenioPorDepartamento = municipioMasPequenioPorDepartamento(data);

            // Mostrar resultados municipio mas grande y pequenio por departamento
            //mostrarResultados3(masGrandePorDepartamento);
            //mostrarResultados4(maspequenioPorDepartamento);

            // Agrupar por departamento y calcular los municipios con mayor y menor densidad de población
            // Agrupar por departamento y calcular los municipios con mayor y menor densidad de población

            Map<String, List<String[]>> municipiosPorDensidad = data.stream()
                    .collect(Collectors.groupingBy(
                            line -> line[1].trim(), // Agrupar por nombre del departamento
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    municipios -> {
                                        // Ordenar municipios por densidad de población (usando la columna URBANA como población aproximada)
                                        return municipios.stream()
                                                .sorted(Comparator.comparingDouble(line -> {
                                                    double area = Double.parseDouble(line[4].trim()); // Columna de superficie
                                                    double poblacion = Double.parseDouble(line[5].trim()); // Columna de población (URBANA)
                                                    return poblacion / area; // Densidad de población
                                                }))
                                                .collect(Collectors.toList());
                                    }
                            )
                    ));

            municipiosPorDensidad.entrySet().stream()
                    .filter(entry -> entry.getValue().size() > 1) // Filtrar departamentos con más de un municipio
                    .forEach(entry -> {
                        String departamento = entry.getKey();
                        List<String[]> municipiosOrdenados = entry.getValue();
                        String[] menorDensidad = municipiosOrdenados.get(0);
                        String[] mayorDensidad = municipiosOrdenados.get(municipiosOrdenados.size() - 1);

                        // Imprimir resultados
                        System.out.println("Departamento: " + departamento);
                        System.out.println("Municipio con menor densidad: " + menorDensidad[3].trim() +
                                ", Densidad: " + calcularDensidad(menorDensidad));
                        System.out.println("Municipio con mayor densidad: " + mayorDensidad[3].trim() +
                                ", Densidad: " + calcularDensidad(mayorDensidad));
                        System.out.println();
                    });

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Función para calcular la densidad de un municipio
    private static double calcularDensidad(String[] municipio) {
        double area = Double.parseDouble(municipio[4].trim()); // Columna de superficie
        double poblacion = Double.parseDouble(municipio[5].trim()); // Columna de población (URBANA)
        return poblacion / area; // Densidad de población
    }

    //metodo para encontrar el municipio más pequenio por superficie en cada departamento
    private static Map<String, Optional<String[]>> municipioMasPequenioPorDepartamento (List<String[]> data){
        return data.stream()
                .collect(Collectors.groupingBy(
                        line -> line[1],  // Agrupar por nombre del departamento
                        Collectors.minBy(Comparator.comparingDouble(line -> Double.parseDouble(line[4]))) // Comparar por superficie
                ));
    }

    //metodo para encontrar el municipio más grande por superficie en cada departamento
    private static Map<String, Optional<String[]>> municipioMasGrandePorDepartamento (List<String[]> data){
        return data.stream()
                .collect(Collectors.groupingBy(
                        line -> line[1],  // Agrupar por nombre del departamento
                        Collectors.maxBy(Comparator.comparingDouble(line -> Double.parseDouble(line[4]))) // Comparar por superficie
                ));
    }

    //resultados del municipio mas pequenio en superficie de cada departamento
    private static void mostrarResultados4(Map<String, Optional<String[]>> municipioMaspequenioPorDepartamento) {
        municipioMaspequenioPorDepartamento.forEach((departamento, municipioOpt) -> {
            if (municipioOpt.isPresent()) {
                String[] municipio = municipioOpt.get();
                System.out.println("Departamento: " + departamento + ", Municipio más pequenio: " + municipio[3].replace("\"", ""));
            }
        });
    }

    //resultados del municipio mas grande en superficie de cada departamento
    private static void mostrarResultados3(Map<String, Optional<String[]>> municipioMasGrandePorDepartamento) {
        municipioMasGrandePorDepartamento.forEach((departamento, municipioOpt) -> {
            if (municipioOpt.isPresent()) {
                String[] municipio = municipioOpt.get();
                System.out.println("Departamento: " + departamento + ", Municipio más grande: " + municipio[3].replace("\"", ""));
            }
        });
    }

    //resultados del promedio de los municipios por departamento
    private static void mostrarResultados2(Map<String, Double> areaPorDepartamento) {
        areaPorDepartamento.forEach((departamento, areaPromedio) -> {
            System.out.println("Departamento: " + departamento + ", Área promedio: " + areaPromedio);
        });
    }

    //metodo para agrupar por departamento y calcular el área promedio de los municipios
    private static Map<String, Double> areaPromedioPorDepartamento (List<String[]> data){
        return data.stream()
                .collect(Collectors.groupingBy(
                        // Agrupar por nombre del departamento
                        line -> line[1],
                        // Calcular el área promedio de los municipios en el departamento
                        Collectors.averagingDouble(line -> Double.parseDouble(line[4]))
                ));
    }
    // Método para cargar los datos desde el archivo CSV
    private static List<String[]> cargarDatos(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        return lines.stream()
                .skip(1)  // Saltar la primera línea (cabecera)
                .map(line -> line.split(";"))
                .collect(Collectors.toList());
    }

    // Método para procesar los datos, agrupar y calcular las estadísticas por departamento
    private static Map<String, Departamento> procesarDatos(List<String[]> data) {
        return data.stream()
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
    }
    // Método para mostrar resultados de cada departamento
    private static void mostrarResultados(Map<String, Departamento> departamentos) {
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
            System.out.println("Porcentaje de población urbana: " + porcentajeUrbana);
            System.out.println("Porcentaje de población rural: " + porcentajeRural);
            System.out.println();
        });
    }
}