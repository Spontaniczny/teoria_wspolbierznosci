package lab3.Waiter;

public class Person implements Runnable{


    private Waiter waiter;
    private int ID;

    public Person(Waiter waiter, int ID){
        this.waiter = waiter;
        this.ID = ID;
    }

    @Override
    public void run() {
        int numberOfMeals = 3;
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
