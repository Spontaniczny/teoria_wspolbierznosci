package lab1.producer_consument_problem;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int repeater;
    private String myID;

    public Consumer(Buffer buffer, int repeater, String ID) {
        this.buffer = buffer;
        this.repeater = repeater;
        this.myID = ID;
    }

    public void run() {

        for(int i = 0;  i < this.repeater;   i++) {
            String message = buffer.take(myID);
        }
    }
}
