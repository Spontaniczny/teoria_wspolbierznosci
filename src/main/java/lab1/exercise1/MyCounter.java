package lab1.exercise1;

class MyCounter{

    public int a = 0;

    synchronized public void add(){
        a += 1;
    }
    synchronized public void subtract(){
        a -= 1;
    }
}