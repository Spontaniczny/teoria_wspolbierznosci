package lab3.Waiter;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    private final Lock lock3 = new ReentrantLock();
    final Condition noPair = lock1.newCondition();
    final Condition noTable = lock2.newCondition();
//    final Condition waitForMeToSit = lock.newCondition();
    ArrayList<Integer> waitingPeopleList = new ArrayList<Integer>();
    private boolean isTableFree = true;
    private int personIDWaitingForPair = -1;
    private int numberOfEatingPeople = 0;


    public Waiter(){

    }


    public void GetReservation(int personID) throws InterruptedException {
        lock1.lock();
//        personIDWaitingForPair = -1;
        waitingPeopleList.add(personID);
        System.out.println("Person: " + personID + " is inside");
        boolean wasIWaiting = false;
        while (!waitingPeopleList.contains(GetPairID(personID))) {
            System.out.println("Person: " + personID + " is waiting for pair: " + GetPairID(personID));
            wasIWaiting = true;
            noPair.await();
//            if(personIDWaitingForPair == GetPairID(personID)){
//                break;
//            }
        }
        System.out.println("Person: " + personID + " found pair");
//        personIDWaitingForPair = personID;
        if (!wasIWaiting) {
            System.out.println("Person: " + personID + " SIGNALS AAAA");
            noPair.signalAll();
        }
        lock1.unlock();
        WaitForTable(personID);
    }
     public void WaitForTable(int personID) throws InterruptedException {
        lock2.lock();
        boolean wasIWaiting = false;
        while(!isTableFree){
            System.out.println("Person: " + personID + " is waiting for the table");
            wasIWaiting = true;
            noTable.await();
            if(personIDWaitingForPair == GetPairID(personID)){
                break;
            }
        }
        System.out.println("Person: " + personID + " is going to the table");
        isTableFree = false;
        personIDWaitingForPair = personID;
        if(!wasIWaiting) {
            noTable.signalAll();
        }
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
            noTable.signalAll();
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


}
