package pkgsynchronized.network.CodeSystem;
import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class
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
