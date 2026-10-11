import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {

        LanzadorProcesos lp = new LanzadorProcesos();
        lp.lanzarAplicacion("Calculator");
        lp.reproducirVoz("Vengaaaaaaaa que ya es casi finde");
        //EsperarProceso esperarProceso = new EsperarProceso();


        //CierreProceso cierreProceso = new CierreProceso();

        //LanzadorProcesos lanzador = new LanzadorProcesos();
        //lanzador.lanzarAplicacion("Calculator");
        //lanzador.reproducirVoz("Proceso hijo iniciado correctamente");


        //CierreProceso cierreProceso = new CierreProceso();
        


        /* Código para lanzar un proceso hijo (por ejemplo, la calculadora de macOS o el bloc de notas) */
        /* 
        try {
            // En macOS se usa 'open -a' para abrir aplicaciones por su nombre
            ProcessBuilder pb = new ProcessBuilder("open", "-a", "Calculator"); 
            // También puedes usar "TextEdit" (equivalente al Bloc de notas) o "Safari"
            
            ProcessBuilder pb1 = new ProcessBuilder("say", "Proceso hijo iniciado correctamente");

            Process proceso = pb.start();
            System.out.println("¡Proceso hijo creado con éxito en macOS! Proceso: " + proceso.toString());
            Process proceso1 = pb1.start();
            System.out.println("¡Proceso hijo creado con éxito en macOS! Proceso: " + proceso1.toString());



        } catch (IOException e) {
            System.err.println("Error al lanzar el proceso: " + e.getMessage());
        }
        */

        /* Código para lanzar un proceso hijo (por ejemplo, la calculadora de Windows o el bloc de notas) */
        /*
        try {
            // En Windows: "calc.exe" o "notepad.exe" | En Linux: "gedit" o "xcalc"
            ProcessBuilder pb = new ProcessBuilder("calc.exe");
            Process proceso = pb.start();
            System.out.println("¡Proceso hijo creado con éxito!");
        } catch (IOException e) {
            System.err.println("Error al lanzar el proceso: " + e.getMessage());
        }*/
    }
}
