package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import shared.UpperCaseServer;
import shared.UpperCaseClient;


public class DumbClient implements UpperCaseClient {
    
    private UpperCaseServer server; // this is my stub

    public DumbClient() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0); // default port
    }

    public void startClient() throws RemoteException, NotBoundException{
        // to make connect to the server
        // getRegistry returns a reference or a stub to 
        // the remote object registry
        // host - host for the remote registry
        // port - port on which the registry accepts requests
        Registry registry = LocateRegistry.getRegistry("localhost",1099);

        // name - the name for the remote reference to look up
        // returns a reference to a remote object
        // Registry returns a Remote object (UpperCaseServer extends
        // from Remote)
        server = (UpperCaseServer) registry.lookup("Server");
    }

    // the method to print upercase
    public void toUpperCase(String argument) throws RemoteException{
        
        try {
            server.toUpperCase(argument, this);
        } catch (RemoteException e) {
            // e.printStackTrace();
            System.out.println("Server disconnected.");
            throw new RuntimeException("Could not connect to the server.");
        }
    }

    @Override
    public void uppercaseResult(String result) throws RemoteException {
        System.out.println("Result > I'm not telling you");
    }
}