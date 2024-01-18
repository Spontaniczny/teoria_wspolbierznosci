package lab4.zad2;

import java.util.Random;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int ID;

    public Consumer(Buffer buffer, int ID) {
        this.buffer = buffer;
        this.ID = ID;
    }

    public void run() {

        for(int i = 0;  i < buffer.numberOfProductions;  i++) {
            try {
                Consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void Consume() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(200, 1000));
        int ConsumptionSize = random.nextInt(1, (int) buffer.bufferSize / 2);
        buffer.Consume(ID, ConsumptionSize);
    }
}
