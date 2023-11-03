package lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorPrinter {
    Printer[] printers;
    final private Lock lock = new ReentrantLock();
    final Condition noPrinters = lock.newCondition();

    public MonitorPrinter(Printer[] printers){
        this.printers = printers;
    }

    public int bookPrinter() throws InterruptedException {
        lock.lock();
        int i=0;
        while(i <= printers.length){
            if(i == printers.length){
                noPrinters.await();
                i = 0;
            }
            if(!printers[i].isBusy){
                printers[i].isBusy = true;
                return i;
            }
            i++;
        }
        lock.unlock();
        return 0;
    }

    public void freePrinter(int id){
        printers[id].isBusy = false;
    }
}
