import java.util.Random;

public class MonteCarlo {
    public static void main(String[] args) {
        int totalSamples = 1_000_000; // número total de puntos
        long insideCircle = 0;        // contador de puntos dentro del círculo

        Random rand = new Random();

        // Generar los puntos aleatorios
        for (int i = 0; i < totalSamples; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();

            if (x * x + y * y <= 1.0) {
                insideCircle++;
            }
        }

        // Aproximación de PI
        double piApprox = (4.0 * insideCircle) / totalSamples;

        // Resultados
        System.out.println("Numero total de puntos: " + totalSamples);
        System.out.println("Puntos dentro del circulo: " + insideCircle);
        System.out.println("Aproximacion de pi: " + piApprox);
        System.out.println("Error: " + Math.abs(piApprox - Math.PI));
    }
}
