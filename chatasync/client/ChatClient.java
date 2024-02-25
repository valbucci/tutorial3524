package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import shared.Message;

public class ChatClient {

    private String host;
    private int port;
    private String username;
    private Socket socket;
    private Scanner scanner;
    private ObjectInputStream streamFromServer;
    private ObjectOutputStream streamToServer;
    private Thread listenerThread;
    private Boolean exitFlag;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.username = null;
        this.socket = null;
        this.scanner = null;
        this.streamFromServer = null;
        this.streamToServer = null;
        this.listenerThread = null;
        this.exitFlag = null;
    }

    private void setup() {
        System.out.println("Setup started.");
        try {
            this.socket = new Socket(this.host, this.port);
            System.out.println("Connected to server.");

            this.scanner = new Scanner(System.in);
            this.streamToServer = new ObjectOutputStream(
                this.socket.getOutputStream()
            );
            this.streamFromServer =  new ObjectInputStream(
                this.socket.getInputStream()
            );
            
            this.exitFlag = false;
            System.out.println("Setup complete!");
        } catch (UnknownHostException e) {
            System.err.println("Unknown host `" + this.host + "`.");
            this.exitFlag = true;
        } catch (IOException e) {
            System.err.println("Could not connect to the server.");
            this.exitFlag = true; // Close the program here
        }
    }

    private void registerUser() {
        if (this.exitFlag) return; // Ensure successful setup

        System.out.println("Insert user name:");
        this.username = this.scanner.nextLine();
        try {
            this.streamToServer.writeObject(this.username);
            System.out.println("Registered to server.");
        } catch (IOException e) {
            System.err.println("Encountered registering the user.");
            this.exitFlag = true;
        }
    }

    private String getUserMessage() {
        System.out.println("Please input >");
        String message = this.scanner.nextLine();
        if (message.equalsIgnoreCase("exit")) {
            this.exitFlag = true;
        }
        return message;
    }

    private void sendUserMessage(String messageString) {
        try {
            Message userMessage = new Message(messageString, this.username);
            this.streamToServer.writeObject(userMessage);
        } catch (IOException e) {
            System.err.println("Failed communicating with the server.");
            this.exitFlag = true;
        }
    }

    private void handleUserInput() {
        // Scanner messageScanner = new Scanner(System.in);
        while(!this.exitFlag) {
            String messageString = this.getUserMessage();
            this.sendUserMessage(messageString);
        }
    }

    private void startListenerThread() {
        this.listenerThread = new Thread(this::listenToServer);
        // Daemon thread: terminates when the program has finished.
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
    }

    private void start() {
        this.startListenerThread();
        this.registerUser();
        this.handleUserInput();
    }

    private void listenToServer() {
        try {
            // keep reading from server and print out.
            while (!this.exitFlag){
                try {
                    Message inMessage =
                        (Message) this.streamFromServer.readObject();
                    System.out.println(inMessage.toString());
                } catch (ClassNotFoundException e) {
                    System.err.println("Could not deserialise the message.");
                }
            }            
        } catch (IOException e) {
            if(!this.exitFlag) {
                System.err.println("Failed while listening to the server.");
                this.exitFlag = true;
            }
        }
    }

    public void run() {
        this.setup();
        this.start();
        this.close();
    }

    private void close() {
        System.out.println("Exiting...");
        try {
            this.scanner.close();
            this.listenerThread.interrupt();
            this.socket.close();
        } catch (NullPointerException e) {
            // The setup failed, nothing to do here
        } catch (IOException e) {
            System.err.println("Failed while closing the socket.");
        }
    }
}
