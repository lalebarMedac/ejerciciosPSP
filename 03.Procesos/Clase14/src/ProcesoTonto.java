import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcesoTonto {
    public static void main(String[] args) throws Exception {
        // El comando es ls -la
        ProcessBuilder pb = new ProcessBuilder(args[0], args[1]);
        Process process = pb.start();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }

        process.waitFor();

    }
}
