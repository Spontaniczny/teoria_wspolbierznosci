package lab2.Semaphore;

class MyCounter{

    public int a = 0;
    Semaphore semaphore = new Semaphore();
    public void add(){
        semaphore.close();
        a += 1;
        semaphore.open();
    }
    public void subtract(){
        semaphore.close();
        a -= 1;
        semaphore.open();
    }
}