package echoexample.echoclient;

import java.io.IOException;
import java.net.UnknownHostException;

public class RunEchoClient {
    public static void main(String[] args) {
        EchoClient client = new EchoClient("139.133.68.160", 50000);
        try {
            client.connect();
            System.out.println("Client starting...");
            client.run();
        } catch (UnknownHostException e) {
            System.out.println("Host does not exist");
        } catch (IOException e) {
            // e.printStackTrace()
            System.out.println("Encountered I/O error");
        }
    }
}
