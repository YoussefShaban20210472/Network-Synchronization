package pkgsynchronized.network.CodeSystem;

import java.util.ArrayList;

public class Router {
    //private ArrayList<Device> Connections;
    private Device[] Connections;
    private Semaphore semaphore;
    int maxConnections;
    //int used = 0;

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
      /*  try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
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
        //Connections[used] = device; //   0 1 2

        System.out.println("Connection "  + index + ":" + device.getConnectionName() + " Occupied");
       // int base = used;
        //used = (used + 1) % maxConnections;
        return index;
        }
    }

    public synchronized void disconnectDevice(Device device) {
       // Connections.remove(device);
        //used--;
        //System.out.println("1");
        Connections[device.getConnectionNumber()] = null;
        semaphore.signal();

    }
}


