package multithreadsockets.server;

public class startEchoServer {
    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.start();
    }
}
