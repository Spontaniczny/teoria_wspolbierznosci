package lab3;

public class Printer implements Runnable{
    int id;
    Boolean isBusy = false;
    public Printer(int id){
        this.id = id;
    }

    public void printerPrint() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Printer with id: " + id + " is printing print.");
    }

    @Override
    public void run() {
        try {
            printerPrint();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
