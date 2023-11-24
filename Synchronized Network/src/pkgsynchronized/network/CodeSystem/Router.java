package pkgsynchronized.network.CodeSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Router {
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


