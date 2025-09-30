import java.util.Random;

public class MontecarloHilos {
    static int globalCount = 0;
    static final Object lock = new Object();

    static class Worker extends Thread {
        private int numSamples;
        private int id;

        Worker(int numSamples, int id) {
            this.numSamples = numSamples;
            this.id = id;
        }

        @Override
        public void run() {
            Random rand = new Random();
            int localCount = 0;
            for (int i = 0; i < numSamples; i++) {
                double x = rand.nextDouble();
                double y = rand.nextDouble();
                if (x * x + y * y <= 1.0) {
                    localCount++;
                }
            }
            synchronized (lock) {
                System.out.println("Hilo " + id + ": +" + localCount + " puntos");
                globalCount += localCount;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int totalSamples = 1_000_000;
        int numThreads = 4;
        int samplesPerThread = totalSamples / numThreads;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Worker(samplesPerThread, i);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        double piApprox = 4.0 * globalCount / totalSamples;
        System.out.println("\n[Threads] Aproximación de π = " + piApprox);
        System.out.println("Error = " + Math.abs(piApprox - Math.PI));
    }
}
