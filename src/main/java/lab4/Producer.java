package lab4;

public class Producer implements Runnable {
    private Buffer buffer;
    private int repeater;
    private String myID;
    private int index;

    public Producer(Buffer buffer, int repeater, String ID) {
        this.buffer = buffer;
        this.repeater = repeater;
        this.myID = ID;
    }

    public void run() {

        for(int i = 0;  i < this.repeater;   i++) {
            String m = "Producer with ID " + this.myID + " put message.";
            buffer.put(m);
        }
    }
}
