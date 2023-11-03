package lab2.ShopSimulator;

public class Semaphore {
    public int is_open = 0;


    synchronized public void open(){
        System.out.println("out");
        is_open += 1;
        notify();
    }


    synchronized public void close(){
        while(is_open == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("in");
        is_open -= 1;
    }
}
