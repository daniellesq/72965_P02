import java.util.Random;
import java.util.stream.IntStream;

public class MontecarloSIMD {
    public static void main(String[] args) {
        int totalSamples = 10_000_000;
        Random rand = new Random();

        // Escalar
        long start = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < totalSamples; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            if (x * x + y * y <= 1.0) {
                count++;
            }
        }
        double piEscalar = 4.0 * count / totalSamples;
        long end = System.currentTimeMillis();
        System.out.println("π Escalar = " + piEscalar + " en " + (end - start) + " ms");

        // Paralelo (similar a SIMD)
        start = System.currentTimeMillis();
        long countParallel = IntStream.range(0, totalSamples)
                .parallel()
                .filter(i -> {
                    double x = Math.random();
                    double y = Math.random();
                    return x * x + y * y <= 1.0;
                })
                .count();
        double piParallel = 4.0 * (double) countParallel / totalSamples;
        end = System.currentTimeMillis();
        System.out.println("π Paralelo = " + piParallel + " en " + (end - start) + " ms");
    }
}
