package lab2.ShopSimulator;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        int customers = 30;
        Shop shop = new Shop();
        Thread[] threads = new Thread[customers];
        for(int i = 0; i < customers; i++){
            threads[i] = new Thread(new Client(shop, i));
            threads[i].start();
        }
        for(int i = 0; i < customers; i++){
            threads[i].join();
        }
    }
}
