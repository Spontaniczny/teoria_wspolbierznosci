package lab3;

public class Main {

    public static void main(String[] args) {
        int numberOfPrinters = 5;
        int numberOfPeople = 10;
        Thread[] threads = new Thread[numberOfPrinters];
        for(int i = 0; i < numberOfPrinters; i++){
            threads[i] = new Thread(new Printer(i));
        }
        MonitorPrinter monitor = new MonitorPrinter(threads);
        for(int i = 0; i < numberOfPeople; i++){
            threads[i].join();
        }
    }
}
