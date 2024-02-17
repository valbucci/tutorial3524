package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shared.UpperCaseServer;

public class RunServer {
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        // If server does run the server, it throws RemoteException
        UpperCaseServer server = new ServerImpl(); // this is the stub

        // Now create a remote object registry that accepts calls
        // on a specific port
        Registry registry = LocateRegistry.createRegistry(1099); //port:1099

        // Then bind this registry to my server
        // name:"Server" can be anything; this is the bind name of the server
        // When the client wants to get the objects of the server,
        // it needs to use the bind name "Server".
        registry.bind("Server", server);

        // then we can print out: server started.
        System.out.println("Server started.");
    }
}