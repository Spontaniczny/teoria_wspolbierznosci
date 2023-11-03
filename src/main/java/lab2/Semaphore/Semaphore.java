package lab2.Semaphore;

public class Semaphore {
    public Boolean is_open = true;


    synchronized public void open(){
        is_open = true;
        notify();
    }


    synchronized public void close(){
        while(!is_open){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        is_open = false;
    }
}
