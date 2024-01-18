package lab4;

public class Buffer {
    public int[] productionLine;
    public int numberOfProducers;


    public Buffer(int productionSize, int numberOfProducers){
        this.numberOfProducers = numberOfProducers;
        this.productionLine = new int[productionSize];
    }


    public synchronized void Produce(int index, int ProducerID){
        while(productionLine[index] != ProducerID){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        productionLine[index] = (ProducerID + 1) % numberOfProducers;
        System.out.println("Produkcja na indexie " + index + " poszła do przodu na nr: " + productionLine[index]);
        notifyAll();
    }
}
