package lab3.MonitorPrinter;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorPrinter {
    boolean[] printers;
    int freePrinters;
    final private Lock lock = new ReentrantLock();
    final Condition noPrinters = lock.newCondition();

    public MonitorPrinter(int numberOfPrinters){
        this.printers = new boolean[numberOfPrinters];
        this.freePrinters = numberOfPrinters;
        for(int i =0; i<numberOfPrinters; i++){
            printers[i] = true;
        }
    }

    public void bookPrinter(int personID) throws InterruptedException {
        lock.lock();
        System.out.println("Person " + personID + " is inside");
        while (freePrinters == 0){
            System.out.println("Person " + personID + " is waiting");
            noPrinters.await();
        }
        freePrinters--;
        int i=0;
        while(i < printers.length){
            if(printers[i]){
                printers[i] = false;
                System.out.println("Person " + personID + " booked printer " + i + ". Free printers: " + freePrinters);
                lock.unlock();
                useAndFreePrinter(i, personID);
                return;
            }
            i++;
        }
    }

    private void useAndFreePrinter(int printerID, int personID) throws InterruptedException {
        Random random = new Random();

        int sleepTime = random.nextInt(200, 1000);  // zapytaj co sie dzieje z 1 sekunda stale
        Thread.sleep(sleepTime);
        lock.lock();
        freePrinters++;
        printers[printerID] = true;
        System.out.println("Person " + personID + " and printer " + printerID + " free" + ". Free printers: " + freePrinters);
        noPrinters.signal();
//        Thread.sleep(200);
        lock.unlock();
    }
}
