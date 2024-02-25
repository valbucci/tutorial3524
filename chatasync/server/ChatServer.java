package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private int port;
    private ServerSocket serverSocket;
    private ConnectionPool connectionPool;

    public ChatServer(int port) {
        this.port = port;
        this.serverSocket = null;
        this.connectionPool = null;
    }

    private void setup() throws IOException {
        System.out.println("ChatServer starting...");
        this.serverSocket = new ServerSocket(this.port);
        // make a connection pool for all the connecting clients.
        this.connectionPool = new ConnectionPool();
        System.out.println("Setup complete!");
        
    }

    private ChatServerHandler awaitClientConnection() {
        System.out.println("Waiting for new client connection...");
        try {
            Socket socket = this.serverSocket.accept();
            System.out.println("New client connected.");

            // create server_socket_handler and start it.
            ChatServerHandler handler = new ChatServerHandler(
                socket,
                this.connectionPool
            );
            this.connectionPool.addConnection(handler);
            return handler;

        } catch (IOException e) {
            // e.printStackTrace();
            System.err.println("Could not establish connection with client.");
            return null;
        }
    }

    private void start() {
        while (true){
            ChatServerHandler handler = this.awaitClientConnection();
            if (handler != null) {
                // Start chat listener thread 
                Thread chatThread = new Thread(handler);
                chatThread.start();
            } else {
                // If a client failed connecting stop the server.
                // You could also do nothing here and just continue listening
                // for new connections.
                break;
            }
        }
    }

    public void run() {
        try {
            this.setup();
        } catch (IOException e) {
            // If setup failed, stop here
            System.err.println("Setup failed; aborting...");
            return;
        }
        this.start();
        System.out.println("Server stopped.");
    }

}
