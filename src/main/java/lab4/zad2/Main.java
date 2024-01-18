package lab4.zad2;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        int size = 100;
        int numberOfProducers = 5;
        int numberOfConsumers = 5;
        Buffer buffer = new Buffer(size);
        Thread[] threads = new Thread[numberOfProducers + numberOfConsumers];
        int i;
        for (i = 0; i < numberOfProducers; i++) {
            threads[i] = new Thread(new Producer(buffer, i));
            threads[i].start();
        }
        for (i = numberOfProducers; i < numberOfProducers + numberOfConsumers; i++) {
            threads[i] = new Thread(new Consumer(buffer, i));
            threads[i].start();
        }

        for (i = 0; i < numberOfProducers + numberOfConsumers; i++) {
            threads[i].join();
        }

    }
}
