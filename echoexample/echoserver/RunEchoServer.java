package echoexample.echoserver;

import java.io.IOException;

public class RunEchoServer {
    public static void main(String[] args) {
        // java echoexample.echoserver.echoServerStart --no-stopping --port-number=10000
        // args = ["--no-stopping", "--port-number=10000"]

        EchoServer server = new EchoServer(50000);
        try {        
            server.connect();
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("Encountered error during server startup");
        }
        server.run();
    }
}
