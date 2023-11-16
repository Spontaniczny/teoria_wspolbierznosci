package lab3.Philosophers;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private int numberOfPhilosophers;
    private int numberOfFightingPhilosophers = 0;
    private boolean isWaiterAlive;
    Random random = new Random();
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    final Condition tooManyPhilosophers = lock1.newCondition();
    final Condition noLeftFork = lock2.newCondition();
    final Condition noRightFork = lock2.newCondition();



    private boolean[] forks;


    public Waiter(int numberOfPhilosophers, boolean isWaiterAlive){
        this.isWaiterAlive = isWaiterAlive;
        this.numberOfPhilosophers = numberOfPhilosophers;
        this.forks = new boolean[numberOfPhilosophers];
        for(int i = 0; i < numberOfPhilosophers; i++){
            this.forks[i] = true;
        }
    }

    public void PhilosopherWantsToEat(int philosopherID) throws InterruptedException {
        lock1.lock();
        if(isWaiterAlive){
            while (numberOfFightingPhilosophers == numberOfPhilosophers - 1) {
                System.out.println("Philosopher " + philosopherID + " waits. STOPPED BY WAITER");
                tooManyPhilosophers.await();
            }
        }
        numberOfFightingPhilosophers++;
        lock1.unlock();
        int randomNumber = random.nextInt(1, 3);
        if(randomNumber == 2){
            takeLeftFork(philosopherID);
            takeRightFork(philosopherID);
        }
        else{
            takeRightFork(philosopherID);
            takeLeftFork(philosopherID);
        }
        philosopherEat(philosopherID);
        lock1.lock();
        numberOfFightingPhilosophers--;
        tooManyPhilosophers.signal();
        lock1.unlock();
    }
    private void takeLeftFork(int philosopherID) throws InterruptedException {
        lock2.lock();
        while (!forks[philosopherID]){
            System.out.println("Philosopher " + philosopherID + " waits for fork: " + philosopherID);
            noLeftFork.await();
        }
        forks[philosopherID] = false;
        System.out.println("Philosopher " + philosopherID + " took fork: " + philosopherID);
        lock2.unlock();
    }

    private void takeRightFork(int philosopherID) throws InterruptedException {
        lock2.lock();
        while (!forks[(philosopherID + 1) % numberOfPhilosophers]){
            System.out.println("Philosopher " + philosopherID + " waits for fork: " + (philosopherID + 1) % numberOfPhilosophers);
            noRightFork.await();
        }
        forks[(philosopherID + 1) % numberOfPhilosophers] = false;
        System.out.println("Philosopher " + philosopherID + " took fork: " + (philosopherID + 1) % numberOfPhilosophers);
        lock2.unlock();
    }

    private void philosopherEat(int philosopherID) throws InterruptedException {
        System.out.println("Philosopher " + philosopherID + " is eating");
        Thread.sleep(random.nextInt(1000, 2000));
        lock2.lock();
        forks[(philosopherID + 1) % numberOfPhilosophers] = true;
        forks[philosopherID] = true;
        System.out.println("Philosopher " + philosopherID + " gave back forks: " + philosopherID + " " + (philosopherID + 1) % numberOfPhilosophers);
        noLeftFork.signalAll();
        noRightFork.signalAll();
        lock2.unlock();
    }
}
