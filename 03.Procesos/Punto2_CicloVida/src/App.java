import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
    try {
        // Abrimos la Calculadora (macOS: "open -a Calculator" | Windows: "calc.exe")
        ProcessBuilder pb = new ProcessBuilder("/bin/sleep", "10"); // Simulamos una calculadora con sleep de 30 segundos
        Process proceso = pb.start();

        System.out.println("PADRE: Calculadora iniciada. Esperando 4 segundos...");
        Thread.sleep(4000);

        // Comprobamos si el proceso sigue vivo antes de destruirlo
        if (proceso.isAlive()) {
            System.out.println("PADRE: El tiempo ha expirado. Destruyendo el proceso...");
            proceso.destroy(); // Finalización forzada
        }

        // Esperamos la confirmación de salida
        int codigoSalida = proceso.waitFor();
        System.out.println("PADRE: El proceso ha terminado con código: " + codigoSalida);

    } catch (IOException | InterruptedException e) {
        System.err.println("Error en la gestión del ciclo de vida: " + e.getMessage());
    }
    }
}