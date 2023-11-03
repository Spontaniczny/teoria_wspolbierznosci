package lab2.ShopSimulator;

public class Client implements Runnable {

    public Shop shop;
    public int ID;
    public Client(Shop shop, int ID){
        this.shop = shop;
        this.ID = ID;
    }


    @Override
    public void run() {
        shop.customerIn();
        System.out.println("Customer in " + ID);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        shop.customerOut();
        System.out.println("Customer out " + ID);
    }
}
