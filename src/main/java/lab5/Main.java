package lab5;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        int[] threadNumbers = {1, 4, 8};
        int[] MAX_ITERS = {500, 1000, 5000, 10000};

        final int runs = 10;
        final int width = 500;
        final int height = 500;
        final int zoom = 150;

        FileWriter file = new FileWriter("results.csv");
        file.append("Iterations,Threads,Tasks,Average,Deviation\n");

        for (int MAX_ITER : MAX_ITERS) {
            for (int threadNo : threadNumbers) {
                int[] taskNumbers = { threadNo, 10 * threadNo, width * height };
                for (int taskNo : taskNumbers) {
                    ArrayList<Long> times = new ArrayList<>();
                    System.out.println(threadNo+" "+taskNo);
                    for (int i = 0; i < runs; i++) {
                        Mandelbrot mandelbrot = new Mandelbrot(width, height, zoom, MAX_ITER, threadNo, taskNo);
                        long startTime = System.nanoTime();
                        mandelbrot.calculate();
                        times.add(System.nanoTime() - startTime);
                    }

                    long avgTime = 0;
                    for (Long t : times){
                        avgTime += t;
                    }
                    avgTime /= times.size();

                    double devTime = 0.0;
                    for (Long t : times) {
                        devTime += Math.pow(t - avgTime, 2);
                    }
                    devTime = Math.sqrt(devTime / times.size());

                    file.append(MAX_ITER + "," + threadNo + "," + taskNo + "," + avgTime + "," + devTime + "\n");
                }
            }
        }
        file.close();

//        Mandelbrot mandelbrot = new Mandelbrot(600, 600, 125, 1000, 5, 5);
//        mandelbrot.setVisible(false);
//        mandelbrot.calculate();
    }


}
