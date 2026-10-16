public class App {

    public static void main(String[] args) throws Exception {
        ContadorCompartido recurso = new ContadorCompartido();

        // Creamos dos tareas que compiten por la sección crítica
        Runnable tarea = () -> {
            for (int i = 0; i < 3; i++) {
                recurso.incrementar(Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                     Thread.currentThread().interrupt();
                }
            }
        };

        Thread h1 = new Thread(tarea, "Proceso_A");
        Thread h2 = new Thread(tarea, "Proceso_B");

        h1.start();
        h2.start();

        try {
            h1.join();
            h2.join();
            System.out.println("\nVALOR FINAL DEL RECURSO PROTEGIDO: " + recurso.getCuenta());
        } catch (InterruptedException e) {
            System.err.println("Error en la sincronización.");
        }
    }
}
