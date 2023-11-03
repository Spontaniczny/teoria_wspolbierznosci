package lab1.producer_consument_problem;

public class Buffer {

    public String MyBuffer;
    boolean is_empty = true;
    synchronized public String take(String ID){
//        while(MyBuffer == null){
        while(is_empty){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String m = this.MyBuffer;
        this.MyBuffer = null;
        is_empty = true;
        System.out.println("Consumer with ID " + ID + " took message: " + m);
        notifyAll();
        return m;
    }

    synchronized public void put(String s){
//        while(MyBuffer != null){
        while(!is_empty){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.MyBuffer = s;
        is_empty = false;
        System.out.println(s);
        notifyAll();
    }
}
