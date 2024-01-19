package lab4.zad2;


import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        int size = 100;
        int numberOfProductions = 5;
        int numberOfProducers = 3;
        int numberOfConsumers = 2;
        Producer[] producers = new Producer[numberOfProducers];
        Consumer[] consumers = new Consumer[numberOfConsumers];
        Buffer buffer = new Buffer(size, numberOfProductions);
        Thread[] threads = new Thread[numberOfProducers + numberOfConsumers];

        FileWriter fileProducers = new FileWriter("results_producers.csv");
        FileWriter fileConsumers = new FileWriter("results_consumers.csv");

        fileProducers.append("ID,numberOfProductions,minWaitTime,maxWaitTime,totalWaitTime,avgWaitTime\n");
        fileConsumers.append("ID,numberOfProductions,minWaitTime,maxWaitTime,totalWaitTime,avgWaitTime\n");
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i] = new Producer(buffer, i);
            threads[i] = new Thread(producers[i]);
            threads[i].start();
        }
        int j = 0;
        for (int i = numberOfProducers; i < numberOfProducers + numberOfConsumers; i++) {
            consumers[j] = new Consumer(buffer, j);
            threads[i] = new Thread(consumers[j]);
            j++;
            threads[i].start();
        }

        for (int i = 0; i < numberOfProducers + numberOfConsumers; i++) {
            threads[i].join();
        }

        for (int i = 0; i < numberOfProducers; i++) {
            System.out.println(i + "XD");
            fileProducers.append(i + "," + numberOfProductions + "," + producers[i].minWaitTime + "," +
                    producers[i].maxWaitTime + "," + producers[i].totalWaitTime + "," + producers[i].avgWaitTime + "\n");

        }

        for (int i = 0; i < numberOfConsumers; i++) {
            System.out.println(i + "XDD");
            fileConsumers.append(i + "," + numberOfProductions + "," + consumers[i].minWaitTime + "," +
                    consumers[i].maxWaitTime + "," + consumers[i].totalWaitTime + "," + consumers[i].avgWaitTime + "\n");

        }
        fileProducers.close();
        fileConsumers.close();
    }
}
