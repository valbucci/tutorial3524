package multithreadsockets.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSocketHandler implements Runnable {

    private final Socket socket; // final -- cannot re-setting after constructor

    public ServerSocketHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        // run this code when Tread is running.

        // Read and write to socket IP streams
        // read socket input stream that client sent
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            // output stream data to client
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            while (true){// this keeps reading from client and write to client.
            // read data from input stream
            String read_str = (String) inputStream.readObject();
            System.out.println("Received from client: "+read_str);
            
            if (read_str.equalsIgnoreCase("exit")){
                //this stops connection to current client when received "exit".
                socket.close();
                break;
            }

            // do something about the data
            String result = read_str.toUpperCase();

            // output results to client
            outputStream.writeObject(result);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
