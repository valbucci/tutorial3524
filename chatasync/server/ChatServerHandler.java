package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Message;

public class ChatServerHandler implements Runnable{

    private Socket socket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private ConnectionPool pool; // for broadcast message
    private String user_name;

    public ChatServerHandler(Socket socket, ConnectionPool pool){
        this.socket = socket;
        this.pool = pool;

        try {
            this.inStream = new ObjectInputStream(socket.getInputStream());
            this.outStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }

    @Override
    public void run() {
        // run Thread
        try {
            // now firstly read in the user name
            this.user_name = (String) inStream.readObject();

            while (true) {
                // pass message data into Message 
                Message message = (Message) inStream.readObject();
                String msg_boday = message.getMessageBody();
                this.user_name = message.getUser();
                System.out.println(message.toString());

                if (msg_boday.equalsIgnoreCase("exit")) {
                    // this user should be removed from the list
                    pool.removeUser(this);
                    socket.close();
                    break;
                }               
                // make the broadcast here
                pool.broadcast(message); // the message now is sent out to all clients.
                // 
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void sendMessageToClients(Message msg){
        // output message object
        try {
            outStream.writeObject(msg);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return this.user_name;
    }
}
