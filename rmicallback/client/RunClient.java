package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RunClient {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        // create a client object
        RMIClient client = new RMIClient();
        DumbClient dumb = new DumbClient();

        // the client object can connect to the server by using stub (reference)
        // this will retururn a Remote object
        client.startClient();
        dumb.startClient();

        // input something via keyboard
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("Input >");
            String line = in.nextLine();
            if(line.equalsIgnoreCase("exit")) break;

            // this calls the remote method from client
            try {
                Thread clientThread = new Thread(() -> {
                    try {
                        client.toUpperCase(line);
                    } catch (RemoteException e) {
                        System.out.println("Server disconnected.");
                    }
                });
                Thread dumbThread = new Thread(() -> {
                    try {
                        dumb.toUpperCase(line);
                    } catch (RemoteException e) {
                        System.out.println("Server disconnected.");
                    }
                });
                clientThread.start();
                dumbThread.start();
                System.out.println("Doing something else...");
                Thread.sleep(3000);
                System.out.println("Finished doing something else.");
                clientThread.join();
                dumbThread.join();
            } catch (Exception e) {
                System.out.println("Error:"+e.getMessage());
            }
        }
        in.close();
        System.out.println("Exiting...");
    }
}