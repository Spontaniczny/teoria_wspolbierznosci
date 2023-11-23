package lab3.Philosophers;
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophers = 5;
        Thread[] threads = new Thread[numberOfPhilosophers];
        Waiter waiter = new Waiter(numberOfPhilosophers, true);

        int i;
        for(i=0; i < numberOfPhilosophers; i++){
            threads[i] = new Thread(new Philosopher(waiter, i));
            threads[i].start();
        }

        for(i = 0; i < numberOfPhilosophers; i++){
            threads[i].join();
        }
    }
}
