package server;

import java.util.ArrayList;
import java.util.List;

import shared.Message;

public class ConnectionPool {
    private List<ChatServerHandler> connects = new ArrayList<>();

    // add ChatServerHandler into a list
    public void addConnects(ChatServerHandler csh){
        connects.add(csh);
    }

    // broadcast messages
    public void broadcast(Message msg) {
        for (ChatServerHandler cnn:connects){
            if (!cnn.getClientName().equals(msg.getUser())){
                cnn.sendMessageToClients(msg);
            }            
        }
    }

    public void removeUser(ChatServerHandler csh) {
        connects.remove(csh); // remove a chatserverhandler
    }
}
