package lab2.ShopSimulator;


public class Shop {
    private final int maxCustomers = 5;
    public Semaphore semaphore = new Semaphore();

    public Shop(){
        semaphore.is_open = maxCustomers;
    }

    public void customerIn(){
        semaphore.close();
    }
    public void customerOut(){
        semaphore.open();
    }
}
