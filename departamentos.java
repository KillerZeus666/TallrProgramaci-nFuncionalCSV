//Ejemplo1 contar y traer los departamentos
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Collectors;

public class departamentos
{
    
   
    public static void main(String[] args) throws IOException
    {
        List<String> deptos =   Files.readAllLines(Paths.get("Divipola.csv")); 
        deptos.remove(0);
        
       
        List<String> nomDeptos = deptos.stream()
        .map(l -> l.split(";")[3])
        .distinct()
        .collect(Collectors.toList());
        
         System.out.println("Los departamentos de Colombia son: " + nomDeptos.size());
         
         nomDeptos.forEach(System.out::println);
          
    }
}