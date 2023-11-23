package lab5;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER;
    private final double ZOOM;
    private double zx, zy, cX, cY, tmp;

    private int threadNo;
    private int taskNo;
    private int height;
    private int width;
    private BufferedImage image;


    public Mandelbrot(int width, int height, double zoom, int maxIter, int threadNo, int taskNo) {

        super("Mandelbrot Set");
        this.width = width;
        this.height = height;
        this.ZOOM = zoom;
        this.MAX_ITER = maxIter;
        this.threadNo = threadNo;
        this.taskNo = taskNo;

        setBounds(100, 100, width, height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

//        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
//        for (int y = 0; y < getHeight(); y++) {
//            for (int x = 0; x < getWidth(); x++) {
//                zx = zy = 0;
//                cX = (x - 400) / ZOOM;
//                cY = (y - 300) / ZOOM;
//                int iter = MAX_ITER;
//                while (zx * zx + zy * zy < 4 && iter > 0) {
//                    tmp = zx * zx - zy * zy + cX;
//                    zy = 2.0 * zx * zy + cY;
//                    zx = tmp;
//                    iter--;
//                }
//                image.setRGB(x, y, iter | (iter << 8));
//            }
//        }
    }

    public void calculate() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNo);
        List<Future<Integer>> codes = new ArrayList<>();

        if (taskNo != (height * width)) {
            int step = height / (taskNo * threadNo);
            int y1 = 0, y2;
            for (int i = 0; (i + 1) < (taskNo * threadNo); i++) {
                y2 = y1 + step - 1;
                MandelbrotJob job = new MandelbrotJob(0, y1, width - 1, y2,
                        MAX_ITER, width, height, ZOOM, image);
                codes.add(threadPool.submit(job));
                y1 += step;
            }
            MandelbrotJob job = new MandelbrotJob(0, y1, width - 1, height - 1,
                    MAX_ITER, width, height, ZOOM, image);
            Future<Integer> value = threadPool.submit(job);
            value.get();
        } else {
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++) {
                    MandelbrotJob job = new MandelbrotJob(
                            x, y, x, y, MAX_ITER, width, height, ZOOM, image);
                    codes.add(threadPool.submit(job));
                }
        }

        for (Future<Integer> value : codes)
            value.get();

        threadPool.shutdown();

    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

//    public static void main(String[] args) {
//        new Mandelbrot().setVisible(true);
//    }
}