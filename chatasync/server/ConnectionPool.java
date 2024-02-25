package server;

import java.util.ArrayList;
import java.util.List;

import shared.Message;

public class ConnectionPool {
    private List<ChatServerHandler> connections = new ArrayList<>();

    // add ChatServerHandler into a list
    public void addConnection(ChatServerHandler handler){
        connections.add(handler);
    }

    // broadcast messages
    public void broadcast(Message message) {
        for (ChatServerHandler handler: this.connections){
            if (!handler.getClientName().equals(message.getUser())) {
                System.out.println("Relaying to " + handler.getClientName());
                handler.sendMessageToClient(message);
            }            
        }
    }

    public void removeUser(ChatServerHandler handler) {
        // remove the user's connection handler from pool
        connections.remove(handler);
    }
}
