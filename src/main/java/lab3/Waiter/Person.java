package lab3.Waiter;

import java.util.Random;

public class Person implements Runnable{


    private final Waiter waiter;
    private final int ID;

    public Person(Waiter waiter, int ID){
        this.waiter = waiter;
        this.ID = ID;
    }

    @Override
    public void run() {
        int numberOfMeals = 3;
        Random random = new Random();
        int sleepTime = random.nextInt(200, 1000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=0; i < numberOfMeals; i++){
            try {
                waiter.GetReservation(ID);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
