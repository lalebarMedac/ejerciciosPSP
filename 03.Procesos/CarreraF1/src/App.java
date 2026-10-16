import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception {
        
        String[] escuderias = {"Mercedes", "Red Bull", "Ferrari", "Williams"};

        ProcessBuilder[] pb = new ProcessBuilder[escuderias.length];
        Process[] processes = new Process[escuderias.length];

        System.out.println("=============================================");
        System.out.println("  ¡COMIENZA EL GRAN PREMIO DE F1 (PROCESOS)! ");
        System.out.println("=============================================\n");

        String classpath = System.getProperty("java.class.path");
        String java = System.getProperty("java.home") + "/bin/java";

        // 1. Creación y lanzamiento de los procesos hijos
        for (int i = 0; i < escuderias.length; i++) {
            // Nota: Si usas la carpeta 'bin' o 'build/classes', ajusta la ruta del classpath
            pb[i] = new ProcessBuilder(java, "-cp", classpath , "Escuderia", escuderias[i]);
            processes[i] = pb[i].start();
            System.out.println("-> Proceso iniciado para: " + escuderias[i]);
        }

        System.out.println("\n--- RETRANSMISIÓN EN TIEMPO REAL ---\n");

        // 2. Lectura concurrente de la salida de cada coche
        for (int i = 0; i < processes.length; i++) {
            final int index = i;
            final String nombreEscuderia = escuderias[i];

            // Creamos un hilo en el padre por cada coche para leer su salida sin bloquear a los demás
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(processes[index].getInputStream()))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        
                        // Si el hijo imprime la marca de llegada, anunciamos al ganador
                        if (linea.startsWith("LLEGADA:")) {
                            System.out.println("\n🏆 ¡¡" + nombreEscuderia + " HA CRUZADO LA META!! 🏆\n");
                        } else {
                            System.out.println("[" + nombreEscuderia + "] " + linea);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error leyendo datos de " + nombreEscuderia + ": " + e.getMessage());
                }
            }).start();
        }

        // 3. Sincronización: El padre espera a que todos los procesos hijos finalicen
        for (Process p : processes) {
            p.waitFor();
        }

        System.out.println("\n=============================================");
        System.out.println("  CARRERA FINALIZADA: Todos los procesos cerrados");
        System.out.println("=============================================");
    }
}