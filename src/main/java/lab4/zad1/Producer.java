package lab4;

public class Producer implements Runnable {
    private Buffer buffer;
    private int productionTime;
    private int ID;
    private int index = 0;

    public Producer(Buffer buffer, int productionTime, int ID) {
        this.buffer = buffer;
        this.productionTime = productionTime;
        this.ID = ID;
    }

    public void run() {

        for(int i = 0;  i < buffer.productionLine.length * 5;  i++) {
            try {
                Produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void Produce() throws InterruptedException {
//        while(buffer.productionLine[index] != ID){
//            wait();
//        }
        Thread.sleep(productionTime);
        buffer.Produce(index, ID);
        index = (index + 1) % buffer.productionLine.length;
    }
}
