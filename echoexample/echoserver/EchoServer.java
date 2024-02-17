package echoexample.echoserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer{

    private int port;
    private ServerSocket serverSocket; 

    public EchoServer(int port) {
        this.port = port;
        this.serverSocket = null;
    }

    private ObjectOutputStream getStreamToClient(Socket socket) throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }

    private ObjectInputStream getStreamFromClient(Socket socket) throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }

    public void connect() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
    }

    public void run() {
        System.out.println("Starting server ...");
        
        while(true) {
            try {
                // Call accept() method to wait for connections
                Socket socket = this.serverSocket.accept();
                // after connection to client is made, the following code will run
                System.out.println("Client connected.");


                // Read and write to socket IP streams
                // read socket input stream that client sent
                ObjectInputStream inputStream = this.getStreamFromClient(socket);
                // output stream data to client
                ObjectOutputStream outputStream = this.getStreamToClient(socket);

                // read data from input stream
                String read_str = (String) inputStream.readObject();
                System.out.println("Received from client: "+read_str);
                
                // do something about the data
                String result = read_str.toUpperCase();
                // output results to client
                outputStream.writeObject(result);
            
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
                System.out.println("Encountered an error during server execution");
            }
        }

        // System.out.println("Server stopping...");
    }
}
