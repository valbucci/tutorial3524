package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import shared.UpperCaseServer;
import shared.UpperCaseClient;

// This class implements the server interface
public class ServerImpl implements UpperCaseServer {

    // Using the constructor, 
    // the server must export objects to be remotely available.
    public ServerImpl() throws RemoteException{
        // to export this remote object to make it available
        // to receive incoming calls, 
        //using the particular supplied port.
        // port: the port to export the object on
        UnicastRemoteObject.exportObject(this, 0);
    }

    // The method defined in the interface must be implemented.
    @Override
    public void toUpperCase(String str, UpperCaseClient client) throws RemoteException {
        System.out.println("Received string: " + str);

        try {
            Thread.sleep(2000); // 2 seconds
        } catch (InterruptedException e) {
            // e.printStackTrace();
            System.out.println("Interrupted.");
            return;
        }
        
        String result = str.toUpperCase();

        try {
            System.out.println("Responding with: " + result);
            client.uppercaseResult(result);
        } catch(RemoteException e) {
            // e.printStackTrace();
            System.out.println("Client disconnected.");
            return;
        }
    }
}