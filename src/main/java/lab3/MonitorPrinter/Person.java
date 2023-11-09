package lab3.MonitorPrinter;

public class Person implements Runnable{

    private final int ID;
    private final MonitorPrinter monitorPrinter;


    public Person(int ID, MonitorPrinter monitorPrinter){
        this.ID = ID;
        this.monitorPrinter = monitorPrinter;
    }

    @Override
    public void run() {
        int papersToPrint = 3;
        for(int i = 0; i < papersToPrint; i++) {
            try {
                monitorPrinter.bookPrinter(ID);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
