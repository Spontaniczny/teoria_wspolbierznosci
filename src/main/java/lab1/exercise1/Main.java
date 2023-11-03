package lab1.exercise1;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        int repeats = 1_000_000;
        MyCounter counter = new MyCounter();
        MyAdd thread1 = new MyAdd(repeats, counter);
        MySubtract thread2 = new MySubtract(repeats, counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter.a);
    }
}
