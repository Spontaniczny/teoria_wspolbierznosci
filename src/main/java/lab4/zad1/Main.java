package lab4;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        int size = 100;
        int numberOfProducers = 5;
        Buffer buffer = new Buffer(size, numberOfProducers);
        Thread[] threads = new Thread[numberOfProducers];
        int i;


        for (i = 0; i < numberOfProducers; i++) {
            threads[i] = new Thread(new Producer(buffer, 200 + i * 300, i));
            threads[i].start();
        }

        for (i = 0; i < numberOfProducers; i++) {
            threads[i].join();
        }

    }
}
