import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class
class Semaphore {
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
            write("(" + device.getConnectionName() + ") (" + device.getType() + ") arrived and waiting");
            wait();
        }
        else{
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
class Router {
    //private ArrayList<Device> Connections;
    private Device[] Connections;
    private Semaphore semaphore;
    int maxConnections;
    public Router(int maxConnections) {
        this.maxConnections = maxConnections;
        this.semaphore = new Semaphore(maxConnections);
        // this.Connections = new ArrayList<Device>();
        ; this.Connections = new Device[maxConnections];
        for(int i= 0; i < maxConnections;i++ )
        {
            Connections[i] = null;
        }
    }
    public  int connectDevice(Device device) throws InterruptedException {
        semaphore.wait(device);
        synchronized (this)
        {
            int index =0 ;
            for(int i= 0; i < maxConnections;i++ )
            {
                if(Connections[i] == null){
                    Connections[i] = device;
                    index = i;
                    break;
                }
            }
            write("Connection "  + index + ":" + device.getConnectionName() + " Occupied");
            return index;
        }
    }

    public synchronized void disconnectDevice(Device device) {
        Connections[device.getConnectionNumber()] = null;
        semaphore.signal();
    }
    public void write(String text)
    {
        semaphore.write(text);
    }
}
class Device extends Thread{
    private int connectionNumber;
    private String type;
    private String connectionName;
    private Router router;

    public Device(String connectionName, String type) {
        this.connectionName = connectionName;
        this.type = type;
    }

    public String getConnectionName() {
        return this.connectionName;
    }

    public String getType() {
        return this.type;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void run() {
        connect();
        doWork();
        disconnect();
    }
    public void connect() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            this.connectionNumber = router.connectDevice(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        router.write("Connection " + this.connectionNumber + ":" + this.connectionName + " login");
    }
    public void doWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        router.write("Connection " + this.connectionNumber + ":" + this.connectionName + " perfoms online activity");
    }
    public synchronized void disconnect() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        router.write("Connection " + this.connectionNumber + ":" + this.connectionName + " Logged Out");
        router.disconnectDevice(this);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getConnectionNumber() {
        return connectionNumber;
    }
}


public class Network
{
    public static void main(String[] args) {
        int maxNumber,wishesNumber;
        ArrayList<Device> Devices = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("What is the number of WI-FI Connections?");
        maxNumber = input.nextInt();
        System.out.println("What is the number of devices Clients want to connect?");
        wishesNumber = input.nextInt();
        Router router = new Router(maxNumber);
        for(int i = 0;i < wishesNumber;i++)
        {
            Device NewDevice = new Device(input.next(),input.next());
            NewDevice.setRouter(router);
            Devices.add(NewDevice);
        }
        for(int i = 0;i < wishesNumber;i++)
        {
           Devices.get(i).start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
