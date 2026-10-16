import java.util.Scanner;

public class ReceptorData {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Lee la entrada enviada por el padre desde stdin
        if (sc.hasNextLine()) {
            String nombre = sc.nextLine();
            // Imprime la respuesta hacia stdout para que la lea el padre
            System.out.println("¡Hola " + nombre + "! Mensaje procesado correctamente por el hijo.");
        }
        sc.close();
    }
}