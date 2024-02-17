package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Create the interface extends from Remote;
// if no throws RemoteException, it will get invalid interface.
public interface UpperCaseServer extends Remote {
    void toUpperCase(String str, UpperCaseClient client) throws RemoteException;
}