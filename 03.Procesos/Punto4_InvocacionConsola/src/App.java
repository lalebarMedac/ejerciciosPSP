import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            // 1. Detectamos el sistema operativo
            String os = System.getProperty("os.name").toLowerCase();

            ProcessBuilder pb;

            // 2. Elegimos la consola según el sistema operativo
            if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                pb = new ProcessBuilder("/bin/sh");
            } else if (os.contains("win")) {
                pb = new ProcessBuilder("cmd.exe");
            } else {
                System.out.println("Sistema operativo no compatible.");
                return;
            }

            // 3. Iniciamos el proceso hijo
            Process consola = pb.start();

            // 4. Preparamos los flujos de comunicación
            PrintWriter writer = new PrintWriter(consola.getOutputStream(), true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(consola.getInputStream()));

            // 5. Seleccionamos el comando de diagnóstico
            String comando;

            if (os.contains("mac")) {
                comando = "ifconfig";
            } else if (os.contains("win")) {
                comando = "ipconfig";
            } else {
                comando = "ip addr";
            }

            // 6. Enviamos el comando a la consola
            System.out.println("Ejecutando: " + comando);

            writer.println(comando);
            writer.println("exit");

            // 7. Leemos la salida del proceso hijo
            System.out.println("\n=== SALIDA DE LA CONSOLA ===");

            String linea;

            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // 8. Esperamos a que termine el proceso
            int codigoSalida = consola.waitFor();

            System.out.println("\nProceso finalizado.");
            System.out.println("Código de salida: " + codigoSalida);

            // 9. Cerramos los recursos
            writer.close();
            reader.close();

        } catch (IOException e) {
            System.err.println("Error al ejecutar la consola: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La ejecución fue interrumpida.");
        }
    }
}
