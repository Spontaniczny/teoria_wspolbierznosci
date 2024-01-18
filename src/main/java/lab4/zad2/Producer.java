package lab4.zad2;

import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;
    private int ID;

    public Producer(Buffer buffer, int ID) {
        this.buffer = buffer;
        this.ID = ID;
    }

    public void run() {

        for(int i = 0;  i < buffer.numberOfProductions;  i++) {
            try {
                Produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void Produce() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(200, 1000));
        int productionSize = random.nextInt(1, (int) buffer.bufferSize / 2);
        buffer.Produce(ID, productionSize);
    }
}
