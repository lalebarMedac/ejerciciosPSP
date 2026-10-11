import java.io.IOException;

public class CierreProceso {
    public CierreProceso() {
        try {
            // macOS: "open", "-a", "Calculator" | Windows: "calc.exe"
            ProcessBuilder pb = new ProcessBuilder("open", "-a", "Calculator");
            Process proceso = pb.start();
            
            System.out.println("Proceso lanzado. Esperando 3 segundos...");
            Thread.sleep(5000); // Mantiene el proceso activo 3 segundos
            
            proceso.destroy(); // Forzamos la finalización del proceso hijo
            System.out.println("¡Proceso destruido desde Java!");
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}