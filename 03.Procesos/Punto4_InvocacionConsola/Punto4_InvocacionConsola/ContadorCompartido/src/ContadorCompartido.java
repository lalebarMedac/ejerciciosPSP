class ContadorCompartido {
    private int cuenta = 0;

    // Método sincronizado que garantiza exclusión mutua en la sección crítica
    public synchronized void incrementar(String nombreHilo) {
        int actual = cuenta;
        System.out.println("[" + nombreHilo + "] Entrando a sección crítica. Valor actual: " + actual);
        cuenta = actual + 1;
        System.out.println("[" + nombreHilo + "] Saliendo de sección crítica. Nuevo valor: " + cuenta);
    }

    public int getCuenta() {
        return cuenta;
    }
}