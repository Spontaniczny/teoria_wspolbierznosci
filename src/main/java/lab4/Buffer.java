package lab4;

public class Buffer {

    public String MyBuffer;
    boolean is_empty = true;
    public int production_size;
//    private int no_
    public int[] production;


    public Buffer(int production_size){
        this.production_size = production_size;
        this.production = new int[production_size];
    }
    synchronized public String take(int index){
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
//        System.out.println("Consumer with ID " + ID + " took message: " + m);
        notifyAll();
        return m;
    }

    synchronized public void put(String s){
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
