import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            // Detectamos el sistema operativo
            String os = System.getProperty("os.name").toLowerCase();

            ProcessBuilder pb;

            if (os.contains("mac") || os.contains("nix")
                    || os.contains("nux")) {
                pb = new ProcessBuilder("/bin/sh");
            } else if (os.contains("win")) {
                pb = new ProcessBuilder("cmd.exe");
            } else {
                System.out.println("Sistema operativo no compatible");
                return;
            }

            // Iniciamos la consola como proceso hijo
            Process consola = pb.start();

            // Flujo para enviar comandos al proceso hijo
            PrintWriter writer = new PrintWriter(consola.getOutputStream());

            // Flujo para leer la salida del proceso hijo
            BufferedReader reader = new BufferedReader(new InputStreamReader(consola.getInputStream()));

            // Elegimos el comando según el sistema operativo
            String comando = os.contains("mac")
                    ? "ifconfig"
                    : os.contains("win") ? "ipconfig" : "ip addr";

            // Enviamos el comando y cerramos la consola
            writer.println(comando);
            writer.println("exit");
            writer.flush();

            // Mostramos la respuesta
            System.out.println("=== SALIDA DE LA CONSOLA ===");

            String linea;

            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperamos a que termine el proceso
            int codigoSalida = consola.waitFor();

            System.out.println(
                    "Proceso finalizado. Código: " + codigoSalida);

            writer.close();
            reader.close();

        } catch (IOException e) {
            System.err.println(
                    "Error al invocar la consola: " + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ejecución interrumpida.");
        }
    }
}
