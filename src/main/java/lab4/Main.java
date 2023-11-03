package lab4;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        int repeats = 5;
        Buffer buffer = new Buffer();
        Consumer consumer1 = new Consumer(buffer, repeats, "1");
        Producer producer1 = new Producer(buffer, repeats, "1");
        Consumer consumer2 = new Consumer(buffer, repeats, "2");
        Producer producer2 = new Producer(buffer, repeats, "2");
        Consumer consumer3 = new Consumer(buffer, repeats, "3");
        Producer producer3 = new Producer(buffer, repeats, "3");
        Consumer consumer4 = new Consumer(buffer, repeats, "4");
        Producer producer4 = new Producer(buffer, repeats, "4");
        Thread t1 = new Thread(consumer1);
        Thread t2 = new Thread(producer1);
        Thread t3 = new Thread(consumer2);
        Thread t4 = new Thread(producer2);
        Thread t5 = new Thread(consumer3);
        Thread t6 = new Thread(producer3);
        Thread t7 = new Thread(consumer4);
        Thread t8 = new Thread(producer4);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
    }
}

