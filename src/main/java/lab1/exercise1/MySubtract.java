package lab1.exercise1;

public class MySubtract extends Thread{
    int repeater;
    MyCounter counter;
    public MySubtract(int repeater, MyCounter counter){
        this.repeater = repeater;
        this.counter = counter;
    }
    public void run(){
        for(int i=0; i<repeater; i++){
            counter.subtract();
        }
    }
}
