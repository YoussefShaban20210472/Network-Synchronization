package pkgsynchronized.network.CodeSystem;

public class Device extends Thread{
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
            this.connectionNumber = router.connectDevice(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection " + this.connectionNumber + ":" + this.connectionName + " login");
    }

    public void doWork() {

        System.out.println("Connection " + this.connectionNumber + ":" + this.connectionName + " perfoms online activity");
    }

    public synchronized void disconnect() {

        System.out.println("Connection " + this.connectionNumber + ":" + this.connectionName + " Logged Out");
        router.disconnectDevice(this);


    }

}
