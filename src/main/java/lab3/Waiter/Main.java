package lab3.Waiter;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfPairs = 2;
        int numberOfPeople = numberOfPairs * 2;
        Thread[] threads = new Thread[numberOfPeople];
        Waiter waiter = new Waiter();

        int i;
        for(i=0; i < numberOfPeople; i++){
            threads[i] = new Thread(new Person(waiter, i));
            threads[i].start();
        }

        for(i = 0; i < numberOfPeople; i++){
            threads[i].join();
        }
    }
}
