package pkgsynchronized.network.CodeSystem;



public class Semaphore {
    private int value;

    public Semaphore(int maxNumber)
    {

        this.value = maxNumber;

    }
    public synchronized void wait(Device device) throws InterruptedException {
        value--;
        if(value < 0){
            System.out.println("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived and waiting");
            wait();
        }
        else{
            System.out.println("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived");
        }
    }
    public synchronized void signal(){
        value++;
        if(value <= 0){
            notify();
        }
    }


}
