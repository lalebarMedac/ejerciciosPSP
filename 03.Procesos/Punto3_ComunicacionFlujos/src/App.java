import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb = new ProcessBuilder(javaBin, "-cp", classpath, "ReceptorData");
            Process proceso = pb.start();

            // 1. Enviamos datos al hijo a través de su OutputStream (stdin del hijo)
            PrintWriter escritor = new PrintWriter(proceso.getOutputStream(), true);
            escritor.println("Alumno_PSP");

            // 2. Leemos la respuesta del hijo desde su InputStream (stdout del hijo)
            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String respuesta = lector.readLine();

            System.out.println("PADRE recibe del HIJO: -> " + respuesta);

            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error de comunicación: " + e.getMessage());
        }
    }
}
