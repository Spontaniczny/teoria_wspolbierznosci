package lab1.exercise1;

public class MyAdd extends Thread{

    int repeater;
    MyCounter counter;
    public MyAdd(int repeater, MyCounter counter){
        this.repeater = repeater;
        this.counter = counter;
    }
    public void run(){
        for(int i=0; i<repeater; i++){
            counter.add();
        }
    }

}
