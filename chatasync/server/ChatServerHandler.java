package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Message;

public class ChatServerHandler implements Runnable{

    private Socket socket;
    private ObjectInputStream streamFromClient;
    private ObjectOutputStream streamToClient;
    private ConnectionPool connectionPool; // for broadcast message
    private String username;

    public ChatServerHandler(Socket socket, ConnectionPool pool){
        this.socket = socket;
        this.connectionPool = pool;

        try {
            this.streamFromClient = new ObjectInputStream(
                socket.getInputStream()
            );
            this.streamToClient = new ObjectOutputStream(
                socket.getOutputStream()
            );
        } catch (IOException e) {
            System.err.println("Failed setting up I/O streams");
        }        
    }


    private void registerUser() throws IOException, ClassNotFoundException {
        try {
            this.username = (String) this.streamFromClient.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed registering user " + this.username);
            // Let the run() function handle this error
            throw e;
        }
        this.connectionPool.broadcast(
            this.getUserMessage("joined the chat!")
        );
    }


    @Override
    public void run() {
        // run Thread
        try {
            this.registerUser();

            while (true) {
                // pass message data into Message 
                Message message = (Message) streamFromClient.readObject();
                String messageBody = message.getMessageBody();
                // Overwrite this.username with the one contained in the message
                this.username = message.getUser();
                System.out.println(message.toString());

                if (messageBody.equalsIgnoreCase("exit")) break;
                // send message to all other clients
                connectionPool.broadcast(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Abruptly interrupted communication with `" 
                                + this.username + "`.");
        } finally {
            this.close();
        }
    }

    private Message getUserMessage(String messageBody) {
        return new Message(messageBody, this.username);
    }

    private void close() {
        // remove user from the global connection pool
        this.connectionPool.removeUser(this);
        try {
            this.socket.close();
        } catch (IOException | NullPointerException e) {
            // There was an I/O exception or the socket was not instantiated
            // In either case, do nothing.
        } finally {
            this.connectionPool.broadcast(
                this.getUserMessage("just left the chat.")
            );
        }
    }
    
    public void sendMessageToClient(Message msg){
        try {
            // output message object
            streamToClient.writeObject(msg);
        } catch (IOException e) {
            System.err.println("Failed sending message `" + msg.getMessageBody()
                               + "` to `" + this.username + "`.");
        }
    }

    public String getClientName() {
        return this.username;
    }
}
