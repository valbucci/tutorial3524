package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import shared.Message;

public class ChatClient {

    private ObjectInputStream inStream;
    
    public void startClient(){
        try {
            Socket socket = new Socket("localhost", 50000);

            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());

            // make a new thread to keep reading from server and print out message
            Thread thread = new Thread(this::listenToServer); // lambda expression.
            thread.setDaemon(true); // Daemon Thread: if this is the last thread running, it terminate itself.
            thread.start();

            // name the user
            Scanner scanner_user = new Scanner(System.in);
            System.out.println("Insert user name:");
            String user_name = scanner_user.nextLine();

            // write out the username
            // this corresponds to this.user_name = (String) inStream.readObject() in ChatServerHandler.java;
            outStream.writeObject(user_name); 

            // make some input
            Scanner scanner = new Scanner(System.in);
            System.out.println("A client starts...");

            // make it running all the time
            while (true){

                System.out.println("Please input >");
                String str_msg = scanner.nextLine();

                // this message will be put into Message
                Message instanceMessage = new Message(str_msg, user_name);

                // now send the message to the server
                outStream.writeObject(instanceMessage);

                // break the loop when input is "exit"
                if (str_msg.equalsIgnoreCase("exit")){
                    socket.close();
                    break;
                }                
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void listenToServer() {
        // then read objects from the server
        try {
            // keep reading from server and print out.
            while (true){
                Message inMessage = (Message) inStream.readObject();
                System.out.println(inMessage.getUser()+":"+inMessage.getMessageBody());
            }            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
