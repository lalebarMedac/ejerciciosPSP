import java.util.Random;

public class Escuderia {

    // 1. Atributos del coche / escudería
    private String nombre;
    private int distanciaRecorrida;
    private final int meta = 100; // Meta fija en 100 metros
    private Random random;

    // 2. Constructor
    public Escuderia(String nombre) {
        this.nombre = nombre;
        this.distanciaRecorrida = 0;
        this.random = new Random();
    }

    // 3. Método principal para gestionar el bucle de la carrera
    public void iniciarCarrera() {
        while (distanciaRecorrida < meta) {
            int avance = avanzar();
            imprimirProgreso(avance);

            // Pausa de 300 ms para simular el tiempo de avance en pista
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.err.println("Proceso de " + nombre + " interrumpido.");
                break;
            }
        }

        // Marca especial de cruce de meta para informar al proceso padre
        System.out.println("LLEGADA:" + nombre);
    }

    // 4. Métodos auxiliares
    private int avanzar() {
        int avance = random.nextInt(10) + 1; // Avance aleatorio entre 1 y 10m
        distanciaRecorrida += avance;
        return avance;
    }

    private void imprimirProgreso(int avance) {
        // Esta salida por consola es la que escuchará y leerá el Proceso Padre
        System.out.println(nombre + " avanza " + avance + "m. Total: " + distanciaRecorrida + "/" + meta + "m");
    }

    // 5. Punto de entrada ejecutado cuando el padre hace pb.start()
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: Falta el nombre de la escudería como argumento.");
            System.exit(1);
        }

        // Recuperamos la escudería enviada en args[0] desde App
        String nombreEscuderia = args[0];

        // Instanciamos el coche e iniciamos su carrera independiente
        Escuderia coche = new Escuderia(nombreEscuderia);
        coche.iniciarCarrera();
    }
}