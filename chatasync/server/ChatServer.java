package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public void start() {
        try {
            // implement a server socket which waits for request to come in over the network.
            // This creates a server socket bounded to the specified port.
            ServerSocket server_socket = new ServerSocket(50000);

            // make a connection pool for all the comming clients;
            // this makes sure there is a pool instance for this port
            ConnectionPool cp = new ConnectionPool();

            System.out.println("Server started ...");

            // the serve must be able to accept request all the time
            while (true){
                // listens for a connection to be made to this socket and accepts it.
                Socket socket = server_socket.accept();

                // create server_socket_handler and start it.
                ChatServerHandler csh = new ChatServerHandler(socket, cp);
                cp.addConnects(csh); // add ChatSererHandler into connection pool
                Thread th = new Thread(csh);
                this.start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

}
