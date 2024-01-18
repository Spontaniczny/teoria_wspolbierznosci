package lab4.zad2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    public int bufferSize;
    public int freeSpace;
    public int numberOfProductions = 20;

    final private Lock lock = new ReentrantLock();
    final Condition normalLock = lock.newCondition();
    boolean isPriorLockProdFree = true;
    boolean isPriorLockConFree = true;
    final Condition priorityLockProducer = lock.newCondition();
    final Condition priorityLockConsumer = lock.newCondition();




    public Buffer(int bufferSize){
        this.bufferSize = bufferSize;
        this.freeSpace = bufferSize;

    }


    public void Produce(int ProducerID, int productionSize){
        lock.lock();
        long start = System.currentTimeMillis();
        while(freeSpace - productionSize < 0){
            try {
                System.out.println("Producer " + ProducerID + "waits for: " + productionSize + " space");
                if (isPriorLockProdFree){
                    isPriorLockProdFree = false;
                    priorityLockProducer.await();
                }
                else {
                    normalLock.await();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        freeSpace -= productionSize;
        if (!isPriorLockConFree){
            priorityLockConsumer.signal();
            isPriorLockConFree = true;
        }
        else{
            normalLock.signalAll();
        }
        long finish = System.currentTimeMillis();
        long waitedTime = finish - start;
        lock.unlock();
        System.out.println("Producer, " + ProducerID + "added " + productionSize + " products.");
        System.out.println("Free space: " + freeSpace);
    }

    public synchronized void Consume(int ConsumerID, int consumptionSize){
        lock.lock();
        long start = System.currentTimeMillis();
        while(freeSpace + consumptionSize > bufferSize){
            try {
                System.out.println("Consumer " + ConsumerID + "waits for: " + consumptionSize + " food");
                if (isPriorLockConFree){
                    isPriorLockConFree = false;
                    priorityLockConsumer.await();
                }
                else {
                    normalLock.await();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        freeSpace += consumptionSize;
        if (!isPriorLockProdFree){
            priorityLockProducer.signal();
            isPriorLockProdFree = true;
        }
        else{
            normalLock.signalAll();
        }
        long finish = System.currentTimeMillis();
        long waitedTime = finish - start;
        System.out.println("Consumer waited: " + waitedTime);
        lock.unlock();
        System.out.println("Consumer, " + ConsumerID + " consumed " + consumptionSize + " products.");
        System.out.println("Free space: " + freeSpace);
    }
}
