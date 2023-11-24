package pkgsynchronized.network.CodeSystem;

import java.util.ArrayList;

public class Router {
    private ArrayList<Device> Connections;
    private Semaphore semaphore;
    int maxConnections;

    public Router(int maxConnections) {
        this.maxConnections = maxConnections;
        this.semaphore = new Semaphore(maxConnections);
        this.Connections = new ArrayList<Device>();
    }

    public  int connectDevice(Device device) throws InterruptedException {
      /*  try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        semaphore.wait(device);
        synchronized (this){
        Connections.add(device);
        System.out.println("Connection "  + Connections.indexOf(device) + ":" + device.getConnectionName() + " Occupied");
        return Connections.indexOf(device);}
    }

    public synchronized void disconnectDevice(Device device) {
        Connections.remove(device);
        semaphore.signal();
    }
}
