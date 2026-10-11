import java.io.IOException;

public class ControlProcesos {

    public ControlProcesos() {

        try {
            System.out.println("🚀 Lanzando Calculadora...");

            ProcessBuilder pb = new ProcessBuilder(
                    "open", "-a", "Calculator"
            );

            pb.start();

            System.out.println("✅ Calculadora iniciada");
            System.out.println("⏱ Esperando 5 segundos...");

            Thread.sleep(5000);

            System.out.println("💀 Cerrando Calculadora...");

            ProcessBuilder cerrar = new ProcessBuilder(
                    "osascript",
                    "-e",
                    "tell application \"Calculator\" to quit"
            );

            cerrar.start();

            System.out.println("✅ Calculadora cerrada");

        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
