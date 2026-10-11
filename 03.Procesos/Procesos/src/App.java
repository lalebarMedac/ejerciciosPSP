import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
    try {
        // 1. Obtenemos el entorno de Java y el classpath actual
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = Sumador.class.getCanonicalName();

        // 2. Configuramos ProcessBuilder para ejecutar: java -cp <classpath> Sumador 15 25
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className, "15", "25");
        
        // Redirigimos la salida del hijo directamente a la consola del padre
        builder.inheritIO(); 

        System.out.println("PADRE: Lanzando el proceso hijo 'Sumador'...");
        Process proceso = builder.start();
        proceso.waitFor(); // Esperamos a que finalice
        System.out.println("PADRE: Proceso hijo finalizado.");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error en la ejecución: " + e.getMessage());
        }
    }
}
