import java.io.IOException;

public class ControlProcesos2 {

    public ControlProcesos2() {

        try {

            System.out.println("=================================");
            System.out.println("      CONTROL DE PROCESOS");
            System.out.println("=================================");

            // 1. Abrimos Calculadora
            System.out.println("\n🚀 Lanzando Calculadora...");
            Process calculadora = new ProcessBuilder(
                    "open", "-a", "Calculator"
            ).start();

            // 2. Abrimos Notas
            System.out.println("🚀 Lanzando Notas...");
            Process notas = new ProcessBuilder(
                    "open", "-a", "Notes"
            ).start();

            // 3. Abrimos Safari
            System.out.println("🚀 Lanzando Safari...");
            Process safari = new ProcessBuilder(
                    "open", "-a", "Safari"
            ).start();

            System.out.println("\n✅ ¡Las tres aplicaciones están abiertas!");

            System.out.println("\n⏱ Esperando 10 segundos...");
            Thread.sleep(10000);

            // Cerramos Calculadora
            System.out.println("\n💀 Cerrando Calculadora...");
            new ProcessBuilder(
                    "osascript",
                    "-e",
                    "tell application \"Calculator\" to quit"
            ).start();

            // Cerramos Notas
            System.out.println("💀 Cerrando Notas...");
            new ProcessBuilder(
                    "osascript",
                    "-e",
                    "tell application \"Notes\" to quit"
            ).start();

            // Cerramos Safari
            System.out.println("💀 Cerrando Safari...");
            new ProcessBuilder(
                    "osascript",
                    "-e",
                    "tell application \"Safari\" to quit"
            ).start();

            System.out.println("\n=================================");
            System.out.println("       PROCESOS FINALIZADOS");
            System.out.println("=================================");

        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}