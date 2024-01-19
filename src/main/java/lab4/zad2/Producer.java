package lab4.zad2;

import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;
    private int ID;

    long maxWaitTime = -1;
    long minWaitTime = 10000000;
    long totalWaitTime = 0;
    long avgWaitTime;

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
            avgWaitTime = totalWaitTime / buffer.numberOfProductions;
        }
    }

    public void Produce() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(200, 1000));
        int productionSize = random.nextInt(1, (int) buffer.bufferSize / 2 - 1);
        long start = System.currentTimeMillis();
        buffer.Produce(ID, productionSize);

        long finish = System.currentTimeMillis();
        long waitedTime = finish - start;
        if (waitedTime > maxWaitTime) {maxWaitTime = waitedTime;}
        if (waitedTime < minWaitTime) {minWaitTime = waitedTime;}
        totalWaitTime += waitedTime;
    }
}
