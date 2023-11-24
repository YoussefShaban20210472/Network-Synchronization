package pkgsynchronized.network.CodeSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Semaphore {
    private int value;
    private FileWriter file;

    public Semaphore(int maxNumber)
    {

        this.value = maxNumber;
        File test = new File("test.txt");
        if(!test.exists())
        {
            try {
                test.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public synchronized void wait(Device device) throws InterruptedException {
        value--;
        if(value < 0){
            //System.out.println("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived and waiting");
            write("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived and waiting");
            wait();
        }
        else{
            //System.out.println("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived");
            write("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived");
        }
    }
    public synchronized void signal(){
        value++;
        if(value <= 0){
            notify();
        }
    }
    public synchronized void write(String text)
    {
        try {
            file = new FileWriter("test.txt",true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            file.write(text + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
