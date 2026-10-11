public class Sumador {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Se requieren 2 números como argumentos.");
            System.exit(1);
        }
        int n1 = Integer.parseInt(args[0]);
        int n2 = Integer.parseInt(args[1]);
        int suma = n1 + n2;
        System.out.println("Suma realizada por el proceso hijo: " + suma);
    }
}