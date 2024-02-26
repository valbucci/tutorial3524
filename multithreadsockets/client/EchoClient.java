package multithreadsockets.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {
    
    public void runClient(){
        // connect to the server using port number
        // localhost = 127.0.0.1
        // to find another computer IP address: 
        // In the Command Prompt, type 'ipconfig', find the IPv4 address.
        try {
            Socket socket = new Socket("localhost",50000);

            // for writing object to server
            ObjectOutputStream outputStreamToServer = new ObjectOutputStream(socket.getOutputStream());
            // for reading message from the server
            ObjectInputStream inputStreamFromServer = new ObjectInputStream(socket.getInputStream());

            while (true) {// lets keep input from client and send to and from server.
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Input your words:");
                    String string = scanner.nextLine();
                    outputStreamToServer.writeObject(string);

                    if (string.equalsIgnoreCase("exit")){
                        // this will shut down our client
                        socket.close();
                        break;
                    }
                // read results from the server
                String result = (String) inputStreamFromServer.readObject();
                System.out.println("Received from server: "+result);    
            }
        } 
        //catch (UnknownHostException e) {
        //    // TODO Auto-generated catch block
        //    e.printStackTrace();
        //} 
        catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
    }
}
