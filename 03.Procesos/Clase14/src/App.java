public class App {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "ProcesoTonto","ls", "-la");
        pb.inheritIO();
        Process process = pb.start();
        //Thread.sleep(3000);
        //process.destroy();
        //process.destroyForcibly();
        process.waitFor();
        //System.out.println(process.exitValue());
    }





}
