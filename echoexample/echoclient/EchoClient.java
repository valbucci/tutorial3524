package echoexample.echoclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {

    private Socket socket;
    private String host;
    private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.socket = null;
    }

    private ObjectOutputStream getStreamToServer() throws IOException {
        return new ObjectOutputStream(
            this.socket.getOutputStream()
        );
    }

    private ObjectInputStream getStreamFromServer() throws IOException {
        return new ObjectInputStream(
            this.socket.getInputStream()
        );
    }

    public void connect() throws IOException, UnknownHostException {
        this.socket = new Socket(this.host, this.port);
    }

    public String getMessageFromTerminal() {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert your message here: ");
        String message = input.nextLine();
        input.close();
        return message;
    }
 
    public void run() throws UnknownHostException, IOException {
        // connect to the server using port number
        // localhost = 127.0.0.1
        // to find another computer IP address: 
        // In the Command Prompt, type 'ipconfig', find the IPv4 address.
        
        // for writing object to server
        ObjectOutputStream outputStreamToServer = this.getStreamToServer();
        // for reading message from the server
        ObjectInputStream inputStreamFromServer = this.getStreamFromServer();
        // send message to the server
        String message = this.getMessageFromTerminal();
        outputStreamToServer.writeObject(message);

        try {
            String result = (String) inputStreamFromServer.readObject();
            System.out.println("Received from server: "+result);
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
            System.out.println("Received unsupported object from server");
        }

        System.out.println("Client stopping...");
    }
}