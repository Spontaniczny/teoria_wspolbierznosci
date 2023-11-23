package lab5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService =
        Mandelbrot mandelbrot = new Mandelbrot(600, 600, 125, 1000, 5, 5);
//        mandelbrot.setVisible(true);
        mandelbrot.Calculate();
    }


}
