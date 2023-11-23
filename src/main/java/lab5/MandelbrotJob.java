package lab5;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class MandelbrotJob implements Callable<Integer> {
    private final int x1, y1, x2, y2;
    private final int MAX_ITER;
    private final double ZOOM;
    private int height;
    private int width;
    private BufferedImage image;

    public MandelbrotJob(int x1, int y1, int x2, int y2, int maxIter, int width, int height, double zoom, BufferedImage image) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
        this.height = height;
        this.ZOOM = zoom;
        this.MAX_ITER = maxIter;
        this.image = image;
    }

    @Override
    public Integer call() throws Exception {
        final int w_2 = width / 2;
        final int h_2 = height / 2;
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++) {
                double zx, zy, cX, cY, tmp;
                int iter = MAX_ITER;
                zx = zy = 0;
                cX = (x - w_2) / ZOOM;
                cY = (y - h_2) / ZOOM;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                image.setRGB(x, y, iter | (iter << 8));
            }
        return 0;

    }
}
