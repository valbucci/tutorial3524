package multithreadsockets.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer{
    public void start(){
        System.out.println("Starting server ...");
        
        try {
            // create a Server Socket listening to the port 50000
            ServerSocket serverSocket = new ServerSocket(50000);

            // the following is to create multiple threads 
            // when multiple clients connects.
            while (true){
                // Call accept() method to wait for connections
                Socket socket = serverSocket.accept();
                // after connection to client is made, the following code will run
                System.out.println("Client connected.");

                // Now create a server socket thread which uses the current socket
                // to create a thread
                ServerSocketHandler ssh = new ServerSocketHandler(socket);
                Thread th = new Thread(ssh);
                th.start();
            }            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
