package lab3.Waiter;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    private final Condition[] noPair;
    final Condition noTable = lock2.newCondition();
    ArrayList<Integer> waitingPeopleList = new ArrayList<>();
    private boolean isTableFree = true;
    private int personIDWaitingForPair = -1;
    private int numberOfEatingPeople = 0;


    public Waiter(int numberOfPairs){
        this.noPair = new Condition[numberOfPairs];
        for(int i = 0; i<numberOfPairs; i++){
            noPair[i] = lock1.newCondition();
        }
    }


    public void GetReservation(int personID) throws InterruptedException {
        lock1.lock();
        waitingPeopleList.add(personID);
        System.out.println("Person: " + personID + " is inside");
        boolean wasIWaiting = false;
        while (!waitingPeopleList.contains(GetPairID(personID))) {
            System.out.println("Person: " + personID + " is waiting for pair: " + GetPairID(personID));
            wasIWaiting = true;
            noPair[GetNumberOfPair(personID)].await();
        }
        System.out.println("Person: " + personID + " found pair");
        if (!wasIWaiting) {
            System.out.println("Person: " + personID + " SIGNALS AAAA");
            noPair[GetNumberOfPair(personID)].signal();
        }
        lock1.unlock();
        WaitForTable(personID);
    }

     public void WaitForTable(int personID) throws InterruptedException {
        lock2.lock();
        while(!isTableFree && personIDWaitingForPair != GetPairID(personID)){
            System.out.println("Person: " + personID + " is waiting for the table");
            noTable.await();
            if(personIDWaitingForPair == GetPairID(personID)){
                break;
            }
        }
        isTableFree = false;
        personIDWaitingForPair = personID;
        System.out.println("Person: " + personID + " is going to the table");
        noTable.signalAll();
        numberOfEatingPeople++;
        lock2.unlock();
        SitToTable(personID);
        waitingPeopleList.remove(Integer.valueOf(personID));
    }

    private void SitToTable(int personID) throws InterruptedException {
        System.out.println("Person: " + personID + " is eating.");
        Random random = new Random();
        int sleepTime = random.nextInt(200, 1000);
        Thread.sleep(sleepTime);
        System.out.println("Person: " + personID + " finished eating.");
        lock2.lock();
        numberOfEatingPeople--;
        if(numberOfEatingPeople == 0){
            System.out.println("Table is free.");
            isTableFree = true;
            noTable.signal();
        }
        lock2.unlock();
    }


    private int GetPairID(int personID){
        int pairID = personID;
        if(personID % 2 == 0){
            pairID++;
        }
        else {
            pairID--;
        }
        return pairID;
    }

    private int GetNumberOfPair(int personID){
        return (int) (personID / 2);
    }
}
