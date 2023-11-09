package lab3.MonitorPrinter;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfPrinters = 5;
        int numberOfPeople = 10;
        Thread[] threads = new Thread[numberOfPeople];
        MonitorPrinter monitorPrinter = new MonitorPrinter(numberOfPrinters);

        for(int i = 0; i < numberOfPeople; i++){
            threads[i] = new Thread(new Person(i, monitorPrinter));
            threads[i].start();
        }

        for(int i = 0; i < numberOfPeople; i++){
            threads[i].join();
        }
    }
}
