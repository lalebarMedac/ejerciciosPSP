import java.io.IOException;

public class LanzadorProcesos {

    /**
     * Lanza una aplicación instalada en macOS por su nombre.
     * @param nombreApp Nombre de la aplicación (ej: "Calculator", "TextEdit", "Safari")
     */
    public void lanzarAplicacion(String nombreApp) {
        try {
            // Preparamos el comando 'open -a <nombreApp>' para macOS
            ProcessBuilder pb = new ProcessBuilder("open", "-a", nombreApp);
            
            // Iniciamos el proceso hijo
            Process proceso = pb.start();
            
            System.out.println("-> Proceso '" + nombreApp + "' creado con éxito.");
            System.out.println("   Información del proceso: " + proceso.toString());

        } catch (IOException e) {
            System.err.println("Error al lanzar la aplicación " + nombreApp + ": " + e.getMessage());
        }
    }

    /**
     * Ejemplo de método para lanzar la sintesis de voz en macOS ('say').
     * @param mensaje Texto que leerá el sistema por el altavoz.
     */
    public void reproducirVoz(String mensaje) {
        try {
            ProcessBuilder pb = new ProcessBuilder("say", mensaje);
            Process proceso = pb.start();
            System.out.println("-> Proceso de voz iniciado: " + proceso.toString());
        } catch (IOException e) {
            System.err.println("Error al reproducir voz: " + e.getMessage());
        }
    }
}