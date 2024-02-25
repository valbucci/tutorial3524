package server;

public class StartChatServer {
    public static void main(String[] args) {
        ChatServer server = new ChatServer(50000);
        server.run();
    }
}
